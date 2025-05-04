package com.jj.shore.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jj.shore.data.auth.AuthRepository
import com.jj.shore.helpers.isValidEmail
import com.jj.shore.helpers.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().currentUser?.reload()
            } catch (e: Exception) {
                Log.e("AuthViewModel", "User session reload failed", e)
            }
        }
    }

    /**
     * Determines if app should go back to the start
     */
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean> get() = _shouldRestartApp

    /**
     * Determines if app should go to the home screen
     */
    private val _shouldNavigateToHome = MutableStateFlow(false)
    val shouldNavigateToHome: StateFlow<Boolean> get() = _shouldNavigateToHome
    
    init {
        viewModelScope.launch {
            authRepository.currentUserIdFlow.collect { userId ->
                _shouldNavigateToHome.value = userId != null
            }
        }
    }

    /**
     * Sign out user
     */
    fun signOut() {
        try {
            authRepository.signOut()
            _shouldRestartApp.value = true
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error signing out", e)
        }
    }

    /**
     * Delete account
     */
    fun deleteAccount() {
        viewModelScope.launch {
            try {
                authRepository.deleteAccount()
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error deleting account", e)
            }
        }
    }

    /**
     * Signs in the user
     */
    fun signIn(
        email: String,
        password: String,
        errorMessage: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage("Please fill in all fields.")
            return
        }

        viewModelScope.launch {
            try {
                authRepository.signIn(email, password)
                _shouldNavigateToHome.value = true
            } catch (e: Exception) {
                _shouldNavigateToHome.value = false
                errorMessage("Error signing in: ${e.message}")
            }
        }
    }

    /**
     * Handles Register for User
     */
    fun register(
        email: String,
        password: String,
        repeatPassword: String,
        errorMessage: (String) -> Unit
    ) {

        if (!email.isValidEmail()) {
            errorMessage("Invalid email")
            return
        }

        if (!password.isValidPassword()) {
            errorMessage("Invalid password")
            return
        }

        if (password != repeatPassword) {
            errorMessage("Passwords do not match.")
            return
        }

        viewModelScope.launch {
            try {
                authRepository.signUp(email, password)
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                _shouldNavigateToHome.value = false
                errorMessage("Error registering user, ${e.message}")
            }
        }
    }
}
