package com.jj.shore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jj.shore.ui.components.BottomNavigationBar
import com.jj.shore.ui.theme.ShoreTheme

/**
 *  REFERENCES
 *  https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyActivity.kt
 *  https://developer.android.com/codelabs/jetpack-compose-navigation#3
 */

/**
 * Wrapper for the entire App.
 */
@Composable
fun ShoreApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        navigationScreens.find { it.route == currentDestination?.route } ?: Login

    ShoreTheme {
        Scaffold(
            // Bottom Navigation Bar
            bottomBar = {
                Box(Modifier.padding(bottom = 24.dp)) {
                    BottomNavigationBar(
                        allScreens = navigationScreens,
                        onTabSelected = { newScreen ->
                            navController.navigate(newScreen.route)
                        },
                        currentScreen = currentScreen
                    )
                }
            }
        ) { innerPadding ->

            ShoreNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}