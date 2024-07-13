package com.app.roomlo.viewmodels

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.app.roomlo.data.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val profilePictureUrl = MutableStateFlow("")

    fun uploadProfilePicture(uri: Uri, context: Context) {
        val storageReference =
            Firebase.storage.reference.child("profile_pictures/${FirebaseAuth.getInstance().currentUser?.uid}")
        val uploadTask = storageReference.putFile(uri)

        uploadTask.addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { downloadUrl ->
                updateUserProfilePicture(downloadUrl.toString(), context)
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserProfilePicture(url: String, context: Context) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        userRepository.updateProfilePictureUrl(userId, url).addOnCompleteListener {
            if (it.isSuccessful) {
                profilePictureUrl.value = url
                Toast.makeText(context, "Profile picture updated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to update profile picture.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
