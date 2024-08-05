package com.app.roomlo.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.app.roomlo.dataclasses.PropertiesList
import com.app.roomlo.dataclasses.Property
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val preferenceHelper: PreferenceHelper) {

    private val db: FirebaseFirestore = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val userId = preferenceHelper.userId?.trim()

    suspend fun fetchUserProperties(): PropertiesList? {
        val allProperties = mutableListOf<Property>()
        return try {

            userId?.let {
                val userPropertiesCollection = db.collection("Properties")
                    .document(it)
                    .collection("UserProperties")
                    .get()
                    .await()


//                if (userPropertiesCollection != null) {
//                    Log.d(
//                        "PropertyRepository",
//                        "UserProperties collection size for user $userId: ${userPropertiesCollection.documents.size}"
//                    )
//                }

                if (userPropertiesCollection != null) {
                    for (userProperty in userPropertiesCollection.documents) {
                        userProperty.toObject<Property>()?.let { property ->
                            allProperties.add(property)
                        }
                    }
                }
            }

            PropertiesList(propertyList = allProperties)
        } catch (e: FirebaseFirestoreException) {
//            Log.e("PropertyRepository", "Failed to fetch user properties", e)
            PropertiesList(propertyList = emptyList())
        }
    }


    suspend fun fetchAllProperties(): PropertiesList? {
        val allProperties = mutableListOf<Property>()
        return try {
            // Fetch all documents under the "Properties" collection
            val propertiesCollection = db.collection("Properties").get().await()
//            Log.d("PropertyRepository", "Number of documents: ${propertiesCollection.documents.size}")

            // Check if documents exist
            if (propertiesCollection.documents.isEmpty()) {
//                Log.d("PropertyRepository", "No documents found in the Properties collection.")
                return PropertiesList(propertyList = allProperties)
            }

            // Iterate over each user document
            for (document in propertiesCollection.documents) {
                val userId = document.id
//                Log.d("PropertyRepository", "Document ID: $userId")

                // Fetch all documents under the "UserProperties" sub-collection for each user
                val userPropertiesCollection = document.reference.collection("UserProperties").get().await()
//                Log.d("PropertyRepository", "UserProperties collection size for user $userId: ${userPropertiesCollection.documents.size}")

                // Add each property to the allProperties list
                for (userProperty in userPropertiesCollection.documents) {
                    userProperty.toObject<Property>()?.let { property ->
                        allProperties.add(property)
                    }
                }
            }

            // Return the list of properties
            PropertiesList(propertyList = allProperties)
        } catch (e: FirebaseFirestoreException) {
//            Log.e("PropertyRepository", "Failed to fetch all properties", e)
            PropertiesList(propertyList = emptyList())
        }
    }


    suspend fun addPropertyToDatabase(property: Property): Boolean {
        return try {
            val propertyMap = property.toMap().filterValues { it != null }
            val userId = preferenceHelper.userId
            if (userId != null) {
                // Add the property to the UserProperties sub-collection
                db.collection("Properties")
                    .document(userId)
                    .collection("UserProperties")
                    .document(property.propertyName)
                    .set(propertyMap)
                    .await()

                // Add the document with isEmpty field set to false in the Properties collection
                db.collection("Properties")
                    .document(userId)
                    .set(mapOf("isEmpty" to false), SetOptions.merge())
                    .await()
            }
            true
        } catch (e: Exception) {
//            logError("Failed to add property to database", e)
            false
        }
    }


    suspend fun updatePropertyDetails(updatedProperty: Property): Boolean {
        return try {
                val propertyMap = updatedProperty.toMap().filterValues { it != null }

            if (userId != null) {
                db.collection("Properties").document(userId)
                    .collection("UserProperties")
                    .document(updatedProperty.propertyName)
                    .update(propertyMap)
                    .await()
            }
                true
        } catch (e: Exception) {
//            logError("Failed to update property details", e)
            false
        }
    }

    //TODO : Upload property images
    suspend fun uploadPropertyPictures(property: Property, context: Context): Boolean {
        val uriList = property.propertyImages
        if (uriList.isEmpty()) {
            return false
        }
        val propertyName = property.propertyName
//        Log.d("imagelist", "before uploading images $uriList")

        return try {
            coroutineScope {
                uriList.mapIndexed { index, uriString ->
                    async {
                        val imageRef = storageRef.child("property_photos/${property.ownerId}/$propertyName/${propertyName}_${index}")
                        val uri = Uri.parse(uriString)
                        imageRef.putFile(uri)

                    }
                }

            }
                downloadUrls(property)
                true
        } catch (e: Exception) {
            Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private suspend fun downloadUrls(property: Property) {
        val imageList = mutableListOf<String>()
        try {
            coroutineScope {
                val listResult = storageRef.child("property_photos/${property.ownerId}/${property.propertyName}").listAll().await()
                val downloadUrlJobs = listResult.items.map { imageRef ->
                    async {
                        val downloadUrl = imageRef.downloadUrl.await()
                        downloadUrl.toString()
                    }
                }
                imageList.addAll(downloadUrlJobs.awaitAll())
            }
            updatePropertyImages(property, imageList)
        } catch (e: Exception) {
//            Log.e("downloadUrls", "Error downloading URLs: ${e.localizedMessage}", e)
        }
    }




    private suspend fun updatePropertyImages(property: Property, imageList: List<String>): Boolean {
        val db = Firebase.firestore
        val userId = preferenceHelper.userId

        return if (userId != null) {
            val propertyRef = db.collection("Properties")
                .document(userId)
                .collection("UserProperties")
                .document(property.propertyName)

            try {
                propertyRef.update("propertyImages", imageList).await() // Await the update
                true
            } catch (e: Exception) {
//                Log.e("PropertyRepository", "Failed to update property images: ${e.localizedMessage}", e)
                false
            }
        } else {
            false
        }
    }



    private fun Property.toMap(): Map<String, Any?> {
        return mapOf(
            "owner" to owner,
            "propertyName" to propertyName,
            "rent" to rent,
            "sharingType" to sharingType,
            "size" to size,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt,
            "propertyImages" to propertyImages,
            "ownerId" to ownerId,
            "address" to address
        )
    }

//    private fun logError(message: String, exception: Exception) {
////        Log.e("PropertyRepository", "$message: ${exception.localizedMessage}", exception)
//    }
}
