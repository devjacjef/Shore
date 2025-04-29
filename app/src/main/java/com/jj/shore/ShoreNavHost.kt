package com.jj.shore

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.home.HomeScreen
import com.jj.shore.ui.login.LoginScreen
import com.jj.shore.ui.login.LoginViewModel
import com.jj.shore.ui.register.RegisterScreen
import com.jj.shore.ui.settings.SettingsScreen
import com.jj.shore.ui.settings.SettingsViewModel
import com.jj.shore.ui.task.TaskScreen

/**
 * REFERENCES
 *
 * https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyNavHost.kt
 * https://developer.android.com/codelabs/jetpack-compose-navigation#3
 */

@Composable
fun ShoreNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val shouldNavigateToHome by loginViewModel.shouldNavigateToHome.collectAsState()
    val shouldRestartApp by loginViewModel.shouldRestartApp.collectAsState()

    val startDestination = when {
        shouldRestartApp -> {
            if (shouldNavigateToHome) Home.route else Login.route
        }

        else -> Login.route
    }

    // Navigate normally
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = Login.route) {
            LoginScreen(openRegister = {
                navController.navigate(Register.route) {
                    popUpTo(Login.route)
                    launchSingleTop = true
                }
            })
        }
        composable(route = Register.route) {
            RegisterScreen()
        }
        composable(route = Home.route) {
            if (!shouldNavigateToHome) {
                navController.navigate(Login.route) {
                    popUpTo(Login.route)
                    launchSingleTop = true
                }
            } else {
                HomeScreen()
            }
        }
        composable(route = Task.route) { backStackEntry ->
            if (!shouldNavigateToHome) {
                navController.navigate(Login.route) {
                    popUpTo(Login.route)
                    launchSingleTop = true
                }
            } else {
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
                TaskScreen()
            }
        }

        composable(route = Settings.route) {
            if (!shouldNavigateToHome) {
                navController.navigate(Login.route) {
                    popUpTo(Login.route)
                    launchSingleTop = true
                }
            } else {
                SettingsScreen()
            }
        }
    }
}


/**
 *  Took from one of the references.
 *  Comments explain what the code does.
 */
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }