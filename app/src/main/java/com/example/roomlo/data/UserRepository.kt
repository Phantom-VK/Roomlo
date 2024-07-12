package com.example.roomlo.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class UserRepository(context: Context) {

    private val db: FirebaseFirestore = Firebase.firestore
    val preferenceHelper: PreferenceHelper = PreferenceHelper(context)


    suspend fun fetchUserDetails(): User? {
        return try {
            val userId = preferenceHelper.userId
            userId?.let {
                val document = db.collection("Users").document(it).get().await()
                document.toObject<User>()
            }
        } catch (e: FirebaseFirestoreException) {
            null
        }
    }
    suspend fun addUserToDatabase(user: User, uid: String): Boolean {
        return try {
            preferenceHelper.userId = uid
            val userMap = user.toMap().filterValues { it.toString().isNotEmpty() }
            db.collection("Users").document(uid).set(userMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUserDetails(updatedUser: User): Boolean {
        return try {
            val userId = preferenceHelper.userId
            userId?.let {
                val userMap = updatedUser.toMap().filterValues { it.toString().isNotEmpty() }
                db.collection("Users").document(it).update(userMap).await()
                true
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    fun updateProfilePictureUrl(userId: String, url: String): Task<Void> {
        return db.collection("Users").document(userId).update("profileImageUrl", url)
    }


    private fun User.toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "address" to address,
            "email" to email,
            "mobilenumber" to mobilenumber,
            "wpnumber" to wpnumber,
            "isOwner" to preferenceHelper.isOwner,
            "password" to password,
            "uid" to uid
        )
    }
}


