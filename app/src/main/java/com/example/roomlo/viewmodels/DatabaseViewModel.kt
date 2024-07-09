package com.example.roomlo.viewmodels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
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

    fun addUserToDatabase(user: User, context: Context) {
        val userMap = mutableMapOf<String, Any?>(
            "name" to user.name,
            "address" to user.address,
            "email" to user.email,
            "mobilenumber" to user.mobilenumber,
            "wpnumber" to user.wpnumber,
            "isOwner" to user.isOwner,
            "password" to user.password,
            "profileImageUrl" to user.profileImageUrl
        ).filterValues { it.toString().isNotEmpty() }  // Remove empty values

        db.collection("Users")
            .document(user.mobilenumber)
            .set(userMap)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile saved!", Toast.LENGTH_LONG).show()
                Log.d(tag, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.w(tag, "Error writing document", e)
            }
    }

}
