package com.example.roomlo.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseViewModel : ViewModel() {

    private val database = Firebase.database

    var myRef = database.getReference("User").setValue("New user")


    // Create a new user with a first and last name
    private val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )

// Add a new document with a generated ID
val db = Firebase.firestore.collection("users")
    .add(user)
    .addOnSuccessListener { documentReference ->
        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
    }
    .addOnFailureListener { e ->
        Log.w(TAG, "Error adding document", e)
    }

//    fun addUser(user: User, context: Context) {
//        uid?.let { userId ->
//            val userMap = mapOf(
//                "name" to user.name,
//                "address" to user.address,
//                "email" to user.email,
//                "mobilenumber" to user.mobilenumber,
//                "password" to user.password,
//                "wpnumber" to user.wpnumber,
//                "isOwner" to user.isOwner
//            )
//
//            userRef.child(userId).setValue(userMap).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(context, "Failed to update profile!", Toast.LENGTH_LONG).show()
//                }
//            }
//        } ?: run {
//            Toast.makeText(context, "User ID is null", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    fun updateProfilePicture(uri: Uri, context: Context, onSuccess: (Uri) -> Unit, onFailure: (Exception) -> Unit) {
//        uid?.let { userId ->
//            val storageRef = FirebaseStorage.getInstance().getReference("Profile_pictures")
//                .child(userId)
//                .child("profile.jpg")
//            val uploadTask = storageRef.putFile(uri)
//
//            uploadTask.addOnFailureListener { exception ->
//                Toast.makeText(context, "Failed to upload profile picture!", Toast.LENGTH_LONG).show()
//                onFailure(exception)
//            }.addOnSuccessListener { taskSnapshot ->
//                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
//                    onSuccess(downloadUri)
//                }.addOnFailureListener { exception ->
//                    Toast.makeText(context, "Failed to retrieve download URL!", Toast.LENGTH_LONG).show()
//                    onFailure(exception)
//                }
//            }
//        } ?: run {
//            Toast.makeText(context, "User ID is null", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    fun getUserDetails(onUserDetailsFetched: (User?) -> Unit) {
//        uid?.let { userId ->
//            userRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val user = snapshot.getValue(User::class.java)
//                    onUserDetailsFetched(user)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    onUserDetailsFetched(null)
//                }
//            })
//        } ?: run {
//            onUserDetailsFetched(null)
//        }
//    }


}
