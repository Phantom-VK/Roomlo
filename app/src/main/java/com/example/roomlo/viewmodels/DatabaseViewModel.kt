package com.example.roomlo.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.roomlo.data.User
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val tag = "DatabaseViewModel"





//    init {
//        addUserToDatabase()
//    }

     fun addUserToDatabase(user: User) {
         val userMap = hashMapOf(
             "name" to user.name,
             "address" to user.address,
             "email" to user.email,
             "mobilenumber" to user.mobilenumber,
             "wpnumber" to user.wpnumber,
             "isOwner" to user.isOwner,
             "password" to user.password,
             "profileImageUrl" to user.profileImageUrl,

         )
        db.collection("Users")
            .add(userMap)
            .addOnSuccessListener { documentReference ->
                Log.d(tag, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Error adding document", e)
            }
    }

}
