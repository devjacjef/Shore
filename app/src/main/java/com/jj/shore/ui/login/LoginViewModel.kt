package com.jj.shore.ui.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthenticationException
import com.jj.shore.data.auth.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v1/final/app/src/main/java/com/example/makeitso/screens/login/LoginViewModel.kt
 */

/**
 * Method to handle logic for the Login Screen
 */
class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _shouldNavigateToHome = MutableStateFlow(false)
    val shouldNavigateToHome: StateFlow<Boolean> get() = _shouldNavigateToHome.asStateFlow()

    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    init {
        authRepository.addAuthStateListener { user ->
            _shouldNavigateToHome.value = user != null
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true  // Start loading

            try {
                // Sign in logic (assumed to be in AuthRepository)
                authRepository.signIn(email, password)

                // After successful login, navigate to home
                _shouldNavigateToHome.value = true
            } catch (e: Exception) {
                // TODO: Handle exceptions properly
                _shouldNavigateToHome.value = false
                Log.e("LoginViewModel", "Sign-in failed: ${e.message}")
            } finally {
                _isLoading.value = false  // Stop loading, regardless of success or failure
            }
        }
    }
}
