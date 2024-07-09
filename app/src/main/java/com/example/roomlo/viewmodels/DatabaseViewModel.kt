package com.example.roomlo.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val tag = "DatabaseViewModel"

    private val user = hashMapOf(
        "name" to "Vikram",
        "mobilenumber" to "8530292951",
        "isOwner" to false
    )



    init {
        addUserToDatabase()
    }

    private fun addUserToDatabase() {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(tag, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Error adding document", e)
            }
    }

}
