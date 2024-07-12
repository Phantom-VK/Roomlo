package com.example.roomlo.data

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class AuthRepository(context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val preferenceHelper: PreferenceHelper = PreferenceHelper(context)

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    suspend fun signup(email: String, password: String): FirebaseUser? {
        auth.createUserWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    fun signout() {
        auth.signOut()
    }

     fun updateUserIdInPreferences() {
        auth.currentUser?.uid?.let {
            preferenceHelper.userId = it
        }
    }

}

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
