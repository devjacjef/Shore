package com.jj.shore.data.auth

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v2/app/src/main/java/com/google/firebase/example/makeitso/data/repository/AuthRepository.kt
 */

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    val currentUser: FirebaseUser? = authRemoteDataSource.currentUser
    val currentUserIdFlow: Flow<String?> = authRemoteDataSource.currentUserIdFlow

    suspend fun signIn(email: String, password: String) {
        authRemoteDataSource.signIn(email, password)
    }

    suspend fun signUp(email: String, password: String) {
        authRemoteDataSource.register(email, password)
    }

    fun signOut() {
        authRemoteDataSource.signOut()
    }

    suspend fun deleteAccount() {
        authRemoteDataSource.deleteAccount()
    }

    fun addAuthStateListener(onAuthStateChanged: (user: FirebaseUser?) -> Unit) {
        authRemoteDataSource.addAuthStateListener(onAuthStateChanged)
    }
}