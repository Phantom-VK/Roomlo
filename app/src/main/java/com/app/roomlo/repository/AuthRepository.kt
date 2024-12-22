package com.app.roomlo.repository

import com.app.roomlo.viewmodels.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor( private val preferenceHelper: PreferenceHelper, private val sharedViewModel: SharedViewModel) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

     fun updateUserIdInPreference() {
        auth.currentUser?.uid?.let {
            preferenceHelper.userId = it
        }

    }

}

