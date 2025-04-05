package com.jj.shore

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jj.shore.ui.components.BottomNavigationBar
import com.jj.shore.ui.theme.ShoreTheme

/**
 *  REFERENCES
 *  https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyActivity.kt
 *  https://developer.android.com/codelabs/jetpack-compose-navigation#3
 */

@Composable
fun ShoreApp() {
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
        navigationScreens.find { it.route == currentDestination?.route } ?: Home
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
            // NavHost
            ShoreNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}