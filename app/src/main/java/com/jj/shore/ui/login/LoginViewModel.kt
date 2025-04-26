package com.jj.shore.ui.login

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jj.shore.data.service.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v1/final/app/src/main/java/com/example/makeitso/screens/login/LoginViewModel.kt
 */

/**
 * Method to handle logic for the Login Screen
 */
class LoginViewModel(
    private val accountService: AccountService,
    // Maybe add the log service, though I am not very invested in that approach.
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())

    val loginUiState: StateFlow<LoginUiState> = _uiState

    /**
     * Update Email Value
     */
    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    /**
     * Update Password Value
     */
    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    /**
     * Update Error Message
     */
    fun updateErrorMessage(newErrorMessage: String) {
        _uiState.value = _uiState.value.copy(errorMessage = newErrorMessage)
    }

    /**
     * When Sign In is clicked
     * TODO: Implement this further
     */
    suspend fun signIn(
        email: String,
        password: String
        // TODO: More fields may be required
    ) {
        // TODO: Add Better Validation for Email and Password
        if(email.isEmpty()) {
            updateErrorMessage("Please enter a valid email.")
            return;
        }

        if(password.isEmpty()) {
            updateErrorMessage("Please enter your password.")
            return;
        }


        try {
            accountService.authenticate(email, password)
        } catch (e: Exception) {
            updateErrorMessage("Authentication failed: ${e.message}")
        }
    }

    // TODO: Add Forgot Password
}