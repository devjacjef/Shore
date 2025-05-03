package com.jj.shore.ui.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jj.shore.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().currentUser?.reload()
            } catch (e: Exception) {
                Log.e("LoginViewModel", "User session reload failed", e)
            }
        }
    }

    // Track whether the app needs to restart after a sign-out or account deletion
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean> get() = _shouldRestartApp

    // Track if the current user is anonymous
    private val _isAnonymous = MutableStateFlow(true)
    val isAnonymous: StateFlow<Boolean> get() = _isAnonymous

    // Loading state during sign-in
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    // Navigation state to home
    private val _shouldNavigateToHome = MutableStateFlow(false)
    val shouldNavigateToHome: StateFlow<Boolean> get() = _shouldNavigateToHome

    // Initialize listener for authentication state
    init {
        viewModelScope.launch {
            authRepository.currentUserIdFlow.collect { userId ->
                _shouldNavigateToHome.value = userId != null
            }
        }
    }

    // Load the current user and update anonymous status
    fun loadCurrentUser() {
        try {
            val currentUser = authRepository.currentUser
            _isAnonymous.value = currentUser != null && currentUser.isAnonymous
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Error loading current user", e)
        }
    }

    // Sign out the user and trigger app restart
    fun signOut() {
        try {
            authRepository.signOut()
            _shouldRestartApp.value = true
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Error signing out", e)
        }
    }

    // Delete user account and trigger app restart
    fun deleteAccount() {
        viewModelScope.launch {
            try {
                authRepository.deleteAccount()
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error deleting account", e)
            }
        }
    }

    // Handle user sign-in with email and password
    fun signIn(
        email: String,
        password: String,
        errorMessage: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage("Please fill in all fields.")
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                authRepository.signIn(email, password)
                _shouldNavigateToHome.value = true
            } catch (e: Exception) {
                _shouldNavigateToHome.value = false
                errorMessage("Error signing in: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
