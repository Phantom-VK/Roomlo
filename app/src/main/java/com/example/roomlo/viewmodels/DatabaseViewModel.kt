package com.example.roomlo.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlo.data.User
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DatabaseViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val tag = "DatabaseViewModel"
    var currentUserId:String? = null
    var userDetails: User? = null


    fun addUserToDatabase(user: User, context: Context, uid: String?) {
        currentUserId = uid
        val userMap = mutableMapOf<String, Any?>(
            "name" to user.name,
            "address" to user.address,
            "email" to user.email,
            "mobilenumber" to user.mobilenumber,
            "wpnumber" to user.wpnumber,
            "isOwner" to user.isOwner,
            "password" to user.password,
            "profileImageUrl" to user.profileImageUrl,
            "uid" to uid

        ).filterValues { it.toString().isNotEmpty() }  // Remove empty values

        if (uid != null) {
            db.collection("Users")
                .document(uid)
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


    fun getUserDetails(){

        try{
            if (currentUserId != null) {
                db.collection("Users")
                    .document(currentUserId!!)
                    .get()
                    .addOnSuccessListener {
                        val result = it.toObject<User>()
                        if (result != null) {
                            Log.w(tag, "${result.mobilenumber}\n${result.name}\n${result.email}\n")
                            userDetails = result

                        }

                    }
            }

        }catch (e:FirebaseFirestoreException){
            Log.w(tag, "Error getting data", e)

        }


    }

}