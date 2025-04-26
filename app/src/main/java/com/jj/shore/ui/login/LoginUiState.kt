package com.jj.shore.ui.login

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v1/final/app/src/main/java/com/example/makeitso/screens/login/LoginUiState.kt
 */

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String = ""
)