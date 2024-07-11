package com.example.roomlo.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlo.data.AuthRepository
import com.example.roomlo.data.PreferenceHelper
import com.example.roomlo.data.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    context: Context,
    private val dbViewModel: DatabaseViewModel,
    private val sharedViewModel: SharedViewModel,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val authRepository: AuthRepository = AuthRepository(context)
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState
    private val userId = preferenceHelper.userId

    private val _eventChannel = Channel<AuthEvent>()

    private var isOwner: Boolean = false

    fun setUserType(isOwner: Boolean) {
        this.isOwner = isOwner
    }


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
                authRepository.updateUserIdInPreferences()
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
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
                    authRepository.updateUserTypeInPreferences(user.isOwner)
                    authRepository.updateUserIdInPreferences()
                    if (userId != null) {
                        sharedViewModel.fetchUserDetails()
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
}
