package com.jj.shore.data.auth

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v2/app/src/main/java/com/google/firebase/example/makeitso/data/datasource/AuthRemoteDataSource.kt
 */


class AuthRemoteDataSource @Inject constructor(private val auth: FirebaseAuth) {

    /**
     * Get the current user
     */
    val currentUser : FirebaseUser? get() = auth.currentUser

    /**
     * Get the current user ID
     */
    val currentUserIdFlow: Flow<String?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val userId = auth.currentUser?.uid
            trySend(userId).onFailure {
                Log.e("AuthRemoteDataSource", "Failed to send user ID: $it")
            }
            Log.d("AuthRemoteDataSource", "User ID emitted: $userId")
        }

        auth.addAuthStateListener(listener)

        // Important: remove the same listener you added
        awaitClose {
            auth.removeAuthStateListener(listener)
            Log.d("AuthRemoteDataSource", "AuthStateListener removed")
        }
    }

    /**
     * Sign in with email and password
     */
    suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Sign up with email and password
     */
    suspend fun register(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .await()
    }

    /**
     * Sign Out
     */
    fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()
    }

    /**
     * Delete Account
     */
    suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    /**
     * Add Auth State Listener
     */
    fun addAuthStateListener(onAuthStateChanged: (user: FirebaseUser?) -> Unit) {
        auth.addAuthStateListener { firebaseAuth ->
            onAuthStateChanged(firebaseAuth.currentUser)
        }
    }
}