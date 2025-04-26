package com.jj.shore.data.service

import com.jj.shore.data.user.User
import kotlinx.coroutines.flow.Flow

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v1/final/app/src/main/java/com/example/makeitso/model/service/AccountService.kt
 */

/**
 * Interface for Account Service
 */
interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}