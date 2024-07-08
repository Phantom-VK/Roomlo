package com.example.roomlo.viewmodels

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.roomlo.R
import com.example.roomlo.data.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference


class DatabaseViewModel : ViewModel() {

    private val _firebaseDatabase = FirebaseDatabase.getInstance();
    private val databaseReference = _firebaseDatabase.getReference("Users");
    private val uid = AuthViewModel().userId
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference
    fun addUser(user: User, context: Context) {
        //TODO ad funcrionality to add user in firebase
        //from site: https://medium.com/@ehsankhormali/creating-a-user-profile-screen-with-firebase-authentication-firestore-database-and-cloud-storage-4aa61c9149db
        if (uid != null) {
            databaseReference.child(uid).setValue(user).addOnCompleteListener {
                if (it.isSuccessful) {
                    uploadProfilePic()
                } else {
                    Toast.makeText(context, "Failed to update profile", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri =
            Uri.parse("android.resource://com.example.roomlo.viewmodels/${R.drawable.profile_pic}")


    }
}