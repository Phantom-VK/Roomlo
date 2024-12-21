package com.app.roomlo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomlo.dataclasses.PropertiesList
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.repository.PropertyRepository
import com.app.roomlo.dataclasses.User
import com.app.roomlo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _userProperties = MutableStateFlow<PropertiesList?>(null)
    val userProperties: StateFlow<PropertiesList?> = _userProperties

    private val _allProperties = MutableStateFlow<PropertiesList?>(null)
    val allProperties: StateFlow<PropertiesList?> = _allProperties


    fun fetchUserDetails() {
        viewModelScope.launch {
            val userDetails = userRepository.fetchUserDetails()
            _userDetails.value = userDetails

        }

    }

    fun fetchUserProperties() {
        viewModelScope.launch {
            val userProperties = propertyRepository.fetchUserProperties()
            _userProperties.value = userProperties
        }
    }

     fun fetchAllProperties() {
            try {
                viewModelScope.launch{
                    val allProperties = propertyRepository.fetchAllProperties()
                    _allProperties.value = allProperties
                }
            } catch (e: Exception) {
                Log.e("PropertyViewModel", "Error fetching properties", e)
            }
    }
}


