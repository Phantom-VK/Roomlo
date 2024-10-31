package com.app.roomlo.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentCompositionErrors
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomlo.repository.AuthRepository
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.dataclasses.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dbViewModel: UserViewModel,
    private val sharedViewModel: SharedViewModel,
    private val preferenceHelper: PreferenceHelper,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState
    private val userId = preferenceHelper.userId
    private val _eventChannel = Channel<AuthEvent>()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        val currentUser = authRepository.getCurrentUser()
        _authState.value = if (currentUser == null) {
            AuthState.Unauthenticated
        } else {
            AuthState.Authenticated
        }
        if (currentUser != null) {
            fetchAndUpdateUserDetails()
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            viewModelScope.launch {
                _eventChannel.send(AuthEvent.ShowError("Email or password can't be empty"))
            }
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                authRepository.login(email, password)
                authRepository.updateUserIdInPreference()
                _authState.value = AuthState.Authenticated
                fetchAndUpdateUserDetails()
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
            }
        }
    }

     fun fetchAndUpdateUserDetails() {
        viewModelScope.launch {
            sharedViewModel.fetchUserDetails()
            sharedViewModel.userDetails.collect { user ->
                user?.let {
                    preferenceHelper.apply {
                        username = it.name
                        useremail = it.email
                        mobilenumber = it.mobilenumber
                        profileImageUrl = it.profileImageUrl
                        userAddress = it.address
                        wpnumber = it.wpnumber
                        userType = it.userType
                    }
                }
            }
        }
    }

    fun signup(user: User, context: Context) {
        val email = user.email
        val password = user.password
        if (email.isEmpty() || password.isEmpty()) {
            viewModelScope.launch {
                _eventChannel.send(AuthEvent.ShowError("Email or password can't be empty"))
            }
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                authRepository.signup(email, password)?.let { firebaseUser ->
                    dbViewModel.addUserToDatabase(user, context, firebaseUser.uid)
                    authRepository.updateUserIdInPreference()
                    if (userId != null) {
                        fetchAndUpdateUserDetails()
                    }
                }
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun signout() {
        authRepository.signout()
        _authState.value = AuthState.Unauthenticated
    }




}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class AuthEvent {
    data class ShowError(val message: String) : AuthEvent()
    data class Login(val email: String, val password: String):AuthEvent()
}

