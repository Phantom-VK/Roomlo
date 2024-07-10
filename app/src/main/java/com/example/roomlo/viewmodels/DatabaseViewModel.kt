package com.example.roomlo.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlo.data.User
import com.example.roomlo.data.PreferenceHelper
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DatabaseViewModel(
    context: Context
) : ViewModel() {

    private val db = Firebase.firestore
    private val tag = "DatabaseViewModel"
    private val preferenceHelper: PreferenceHelper = PreferenceHelper(context) // Inject PreferenceHelper here

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    init {
        // Initialize preferenceHelper if not already injected
        // preferenceHelper = PreferenceHelper(context)
    }

    fun fetchUserDetails() {
        val currentUserMobileNumber = preferenceHelper.userMobileNumber ?: ""
        Log.d(tag, "Current user mobile number: $currentUserMobileNumber")

        viewModelScope.launch {
            try {
                val document = db.collection("Users").document(currentUserMobileNumber).get().await()
                val result = document.toObject<User>()
                if (result != null) {
                    Log.d(tag, "User details fetched: ${result.mobilenumber}, ${result.name}, ${result.email}")
                    _userDetails.value = result
                }
            } catch (e: FirebaseFirestoreException) {
                Log.w(tag, "Error getting user data", e)
            }
        }
    }

    fun addUserToDatabase(user: User, context: Context, uid: String) {
        Log.d(tag, "Current user mobile number: ${user.mobilenumber}")

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

        db.collection("Users")
            .document(user.mobilenumber)  // Use currentUserMobileNumber for document ID
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

    fun updateUserDetails(mobilenumber:String){

    }
}
