package com.app.roomlo.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomlo.data.Property
import com.app.roomlo.data.PropertyRepository
import com.app.roomlo.data.User
import com.app.roomlo.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val propertyRepository: PropertyRepository
) : ViewModel() {



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

    fun addPropertyToDatabase(property: Property, context: Context, uid: String) {
        viewModelScope.launch {
            val success =propertyRepository.addPropertyToDatabase(property, uid)
            if (success) {
                Toast.makeText(context, "Successfully uploaded property !", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error saving profile", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updatePropertyDetails(updatedUser: Property, context: Context) {
        viewModelScope.launch {
            val success = propertyRepository.updatePropertyDetails(updatedUser)
            if (success) {
                Toast.makeText(context, "Property updated!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error updating profile", Toast.LENGTH_LONG).show()
            }
        }
    }


}
