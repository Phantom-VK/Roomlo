package com.app.roomlo.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.app.roomlo.dataclasses.PropertiesList
import com.app.roomlo.dataclasses.Property
import com.google.firebase.firestore.FirebaseFirestore
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
class PropertyRepository @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) {
    private val db: FirebaseFirestore = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val userId: String? get() = preferenceHelper.userId?.trim()

    suspend fun fetchUserProperties(): PropertiesList {
        return try {
            val allProperties = userId?.let { uid ->
                db.collection("Properties")
                    .document(uid)
                    .collection("UserProperties")
                    .get()
                    .await()
                    .toObjects(Property::class.java)
            } ?: emptyList()

            PropertiesList(propertyList = allProperties)
        } catch (e: Exception) {
            PropertiesList(propertyList = emptyList())
        }
    }

    suspend fun fetchAllProperties(): PropertiesList {
        return try {
            val allProperties = db.collectionGroup("UserProperties")
                .get()
                .await()
                .toObjects(Property::class.java)

            PropertiesList(propertyList = allProperties)
        } catch (e: Exception) {
            PropertiesList(propertyList = emptyList())
        }
    }

    suspend fun addPropertyToDatabase(property: Property): Boolean {
        return try {
            userId?.let { uid ->
                db.collection("Properties")
                    .document(uid)
                    .collection("UserProperties")
                    .document(property.propertyName)
                    .set(property)
                    .await()

                db.collection("Properties")
                    .document(uid)
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
            userId?.let { uid ->
                db.collection("Properties")
                    .document(uid)
                    .collection("UserProperties")
                    .document(updatedProperty.propertyName)
                    .set(updatedProperty, SetOptions.merge())
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun uploadPropertyPictures(property: Property, context: Context): Boolean {
        if (property.propertyImages.isEmpty()) return false

        return try {
            val imageList = coroutineScope {
                property.propertyImages.mapIndexed { index, uriString ->
                    async {
                        val imageRef = storageRef.child("property_photos/${property.ownerId}/${property.propertyName}/${property.propertyName}_$index")
                        val uri = Uri.parse(uriString)
                        imageRef.putFile(uri).await()
                        imageRef.downloadUrl.await().toString()
                    }
                }.awaitAll()
            }
            updatePropertyImages(property, imageList)
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun updatePropertyImages(property: Property, imageList: List<String>): Boolean {
        return try {
            userId?.let { uid ->
                db.collection("Properties")
                    .document(uid)
                    .collection("UserProperties")
                    .document(property.propertyName)
                    .update("propertyImages", imageList)
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
