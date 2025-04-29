package com.jj.shore.helpers

import android.util.Patterns
import java.util.regex.Pattern

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/5c3b28c04f7582c7e60b30f3693d770b46819646/v2/app/src/main/java/com/google/firebase/example/makeitso/ui/signup/PasswordValidator.kt
 */

private const val MIN_PASSWORD_LENGTH = 8
private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASSWORD_LENGTH &&
            Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
}