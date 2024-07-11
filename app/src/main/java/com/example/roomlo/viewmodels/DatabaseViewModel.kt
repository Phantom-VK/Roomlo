package com.example.roomlo.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlo.data.User
import com.example.roomlo.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatabaseViewModel(
    context: Context
) : ViewModel() {

    private val userRepository: UserRepository = UserRepository(context)

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchUserDetails() {
        viewModelScope.launch {
            _loading.value = true
            val result = userRepository.fetchUserDetails()
            _userDetails.value = result
            _loading.value = false
        }
    }

    fun addUserToDatabase(user: User, context: Context, uid: String) {
        viewModelScope.launch {
            val success = userRepository.addUserToDatabase(user, uid)
            if (success) {
                Toast.makeText(context, "Profile saved!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error saving profile", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateUserDetails(updatedUser: User, context: Context) {
        viewModelScope.launch {
            val success = userRepository.updateUserDetails(updatedUser)
            if (success) {
                Toast.makeText(context, "Profile updated!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error updating profile", Toast.LENGTH_LONG).show()
            }
        }
    }
}
