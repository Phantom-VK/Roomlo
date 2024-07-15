package com.app.roomlo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.roomlo.data.Property
import com.app.roomlo.data.PropertyRepository
import com.app.roomlo.data.User
import com.app.roomlo.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _propertyDetails = MutableStateFlow<Property?>(null)
    val propertyDetails: StateFlow<Property?> = _propertyDetails


    fun fetchUserDetails() {
        viewModelScope.launch {
            val userDetails = userRepository.fetchUserDetails()
            _userDetails.value = userDetails
        }
    }

    fun fetchUserProperties(){
        viewModelScope.launch {
            val propertyDetails = propertyRepository.fetchPropertyDetails()
            _propertyDetails.value = propertyDetails
        }
    }

    fun fetchAllProperties(){
        viewModelScope.launch {

        }
    }
}


