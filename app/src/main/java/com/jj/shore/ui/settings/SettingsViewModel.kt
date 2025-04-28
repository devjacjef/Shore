package com.jj.shore.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.shore.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    private val _isAnonymous = MutableStateFlow(true)
    val isAnonymous: StateFlow<Boolean>
        get() = _isAnonymous.asStateFlow()

    fun loadCurrentUser() {
        try {
            val currentUser = authRepository.currentUser
            _isAnonymous.value = currentUser != null && currentUser.isAnonymous
        } catch (e: Exception) {
            Log.e("SettingsViewModel", "Error loading current user", e)
        }
    }

    fun signOut() {
        try {
            authRepository.signOut()
            _shouldRestartApp.value = true
        } catch (e: Exception) {
            Log.e("SettingsViewModel", "Error signing out", e)
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                authRepository.deleteAccount()
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                Log.e("SettingsViewModel", "Error deleting account", e)
            }
        }
    }
}