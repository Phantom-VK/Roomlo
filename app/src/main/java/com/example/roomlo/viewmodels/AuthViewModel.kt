package com.example.roomlo.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomlo.data.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.tasks.await


class AuthViewModel : ViewModel() {

    private val _auth: FirebaseAuth = FirebaseAuth.getInstance()
    val auth = _auth



    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    // Using a Channel to send one-off events like showing a toast message
    private val _eventChannel = Channel<AuthEvent>()
    val eventsFlow = _eventChannel.receiveAsFlow()

    private val dbViewModel = DatabaseViewModel()
    init {
        checkAuthStatus()
        auth.uid?.let { Log.d("DatabaseViewModel", it) }

    }

    private fun checkAuthStatus() {
        _authState.value = if (auth.currentUser == null) {
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
                auth.signInWithEmailAndPassword(email, password).await()
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
                auth.createUserWithEmailAndPassword(email, password).await()
                dbViewModel.addUserToDatabase(user, context, auth.currentUser?.uid)
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
            }
        }

    }

    fun signout() {
        auth.signOut()
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


// Extension function to convert Task to Deferred

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.isSuccessful) {
                cont.resume(it.result, null)
            } else {
                cont.resumeWithException(it.exception ?: RuntimeException("Unknown task exception"))
            }
        }
    }
}
