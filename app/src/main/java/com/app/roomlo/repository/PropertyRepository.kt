package com.app.roomlo.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.app.roomlo.dataclasses.PropertiesList
import com.app.roomlo.dataclasses.Property
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

                userPropertiesCollection?.let { collection ->
                    for (userProperty in collection.documents) {
                        userProperty.toObject<Property>()?.let { property ->
                            allProperties.add(property)
                        }
                    }
                }
            }
            PropertiesList(propertyList = allProperties)
        } catch (e: FirebaseFirestoreException) {
            PropertiesList(propertyList = emptyList())
        }
    }

    suspend fun fetchAllProperties(): PropertiesList? {
        val allProperties = mutableListOf<Property>()
        return try {
            val propertiesCollection = db.collection("Properties").get().await()
            if (propertiesCollection.documents.isEmpty()) {
                return PropertiesList(propertyList = allProperties)
            }

            for (document in propertiesCollection.documents) {
                val userId = document.id
                val userPropertiesCollection = document.reference.collection("UserProperties").get().await()
                for (userProperty in userPropertiesCollection.documents) {
                    userProperty.toObject<Property>()?.let { property ->
                        allProperties.add(property)
                    }
                }
            }
            PropertiesList(propertyList = allProperties)
        } catch (e: FirebaseFirestoreException) {
            PropertiesList(propertyList = emptyList())
        }
    }

    suspend fun addPropertyToDatabase(property: Property): Boolean {
        return try {
            val propertyMap = property.toMap().filterValues { it != null }
            val userId = preferenceHelper.userId
            if (userId != null) {
                db.collection("Properties")
                    .document(userId)
                    .collection("UserProperties")
                    .document(property.propertyName)
                    .set(propertyMap)
                    .await()

                db.collection("Properties")
                    .document(userId)
                    .set(mapOf("isEmpty" to false), SetOptions.merge())
                    .await()
            }
            true
        } catch (e: Exception) {
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
            false
        }
    }

    suspend fun uploadPropertyPictures(property: Property, context: Context): Boolean {
        val uriList = property.propertyImages
        if (uriList.isEmpty()) {
            return false
        }
        val propertyName = property.propertyName

        return try {
            val imageList = mutableListOf<String>()
            coroutineScope {
                uriList.mapIndexed { index, uriString ->
                    async {
                        val imageRef = storageRef.child("property_photos/${property.ownerId}/$propertyName/${propertyName}_${index}")
                        val uri = Uri.parse(uriString)
                        imageRef.putFile(uri).await()
                        val downloadUrl = imageRef.downloadUrl.await()
                        imageList.add(downloadUrl.toString())
                    }
                }.awaitAll()
            }
            updatePropertyImages(property, imageList)
            true
        } catch (e: Exception) {
            Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private suspend fun updatePropertyImages(property: Property, imageList: List<String>): Boolean {
        val userId = preferenceHelper.userId
        return if (userId != null) {
            val propertyRef = db.collection("Properties")
                .document(userId)
                .collection("UserProperties")
                .document(property.propertyName)

            try {
                propertyRef.update("propertyImages", imageList).await()
                true
            } catch (e: Exception) {
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
}
