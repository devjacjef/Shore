package com.jj.shore

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.components.BottomNavigationBar
import com.jj.shore.ui.login.LoginViewModel
import com.jj.shore.ui.theme.ShoreTheme

/**
 *  REFERENCES
 *  https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyActivity.kt
 *  https://developer.android.com/codelabs/jetpack-compose-navigation#3
 */

@Composable
fun ShoreApp() {
    val viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val shouldRestartApp by viewModel.shouldNavigateToHome.collectAsState()

    /**
     * TODO: Add comments explaining this code.
     * navController -
     * currentBackStack -
     * currentDestination -
     * currentScreen -
     */
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        navigationScreens.find { it.route == currentDestination?.route } ?: Login

    // TODO: Research NavController
    ShoreTheme {
        Scaffold(
            // Bottom Navigation Bar
            bottomBar = {
                BottomNavigationBar(
                    allScreens = navigationScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->

            LaunchedEffect(shouldRestartApp) {
                if (shouldRestartApp) {
                    navController.navigate("home") // Navigate to home after successful login
                }
            }

            ShoreNavHost(
                navController = navController,
                isLoading = viewModel.isLoading.value,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}