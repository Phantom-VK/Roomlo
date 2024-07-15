package com.app.roomlo.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository  @Inject constructor (private val preferenceHelper: PreferenceHelper){

    private val db: FirebaseFirestore = Firebase.firestore


    suspend fun fetchPropertyDetails(): Property? {
        return try {
            val userId = preferenceHelper.userId
            userId?.let {
                val document = db.collection("Properties").document(it).get().await()
                document.toObject<Property>()
            }
        } catch (e: FirebaseFirestoreException) {
            null
        }
    }
    suspend fun addPropertyToDatabase(property: Property, uid: String): Boolean {
        return try {
            preferenceHelper.userId = uid
            val userMap = property.toMap().filterValues { it.toString().isNotEmpty() }
            db.collection("Properties")
                .document(uid)
                .set(userMap)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updatePropertyDetails(updatedProperty: Property): Boolean {
        return try {
            val userId = preferenceHelper.userId
            userId?.let {
                val userMap = updatedProperty.toMap().filterValues { it.toString().isNotEmpty() }
                db.collection("Properties").document(it).update(userMap).await()
                true
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    fun updatePropertyPhotos(userId: String, url: String): Task<Void> {
        return db.collection("Properties").document(userId).update("profileImageUrl", url)
    }


    private fun Property.toMap(): Map<String, Any?> {
        return mapOf(
            "owner" to owner,
            "price" to price,
            "sharingType" to sharingType,
            "size" to size,
            "location" to location,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt,
            "profileImageUrl" to profileImageUrl,
            "ownerId" to ownerId
        )
    }
}