package com.example.roomlo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomlo.data.User
import com.example.roomlo.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchUserDetails() {
        viewModelScope.launch {
            _loading.value = true
            val userDetails = userRepository.fetchUserDetails()
            _userDetails.value = userDetails
            _loading.value = false
        }
    }
}

class SharedViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
