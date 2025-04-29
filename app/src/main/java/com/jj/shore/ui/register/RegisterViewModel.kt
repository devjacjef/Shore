package com.jj.shore.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.shore.data.auth.AuthRepository
import com.jj.shore.helpers.isValidEmail
import com.jj.shore.helpers.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/5c3b28c04f7582c7e60b30f3693d770b46819646/v2/app/src/main/java/com/google/firebase/example/makeitso/ui/signup/SignUpViewModel.kt
 */

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    private val _shouldNavigateToHome = MutableStateFlow(false)
    val shouldNavigateToHome: StateFlow<Boolean> get() = _shouldNavigateToHome.asStateFlow()

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