package com.jj.shore.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.ui.AppViewModelProvider

@Composable
fun LoginScreen(
    // Handled through the AppViewModelProvider
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val loginUiState: LoginUiState by viewModel.loginUiState.collectAsState()

    // TODO: Implement the rest of the screen
}