package com.jj.shore.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.login.LoginViewModel

@Composable
fun SettingsScreen(
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Sign Out Button
            Button(onClick = { viewModel.signOut() }) {
                Text("Sign Out")
            }

            // Add a space between buttons (optional)
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(8.dp))

            // Delete Account Button
            Button(onClick = { viewModel.deleteAccount() }) {
                Text("Delete Account")
            }
        }
    }


}