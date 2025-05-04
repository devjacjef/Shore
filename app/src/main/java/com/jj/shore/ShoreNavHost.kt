package com.jj.shore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.home.HomeScreen
import com.jj.shore.ui.auth.LoginScreen
import com.jj.shore.ui.auth.AuthViewModel
import com.jj.shore.ui.auth.RegisterScreen
import com.jj.shore.ui.auth.SettingsScreen
import com.jj.shore.ui.task.TaskFormScreen
import com.jj.shore.ui.task.TaskScreen
import com.jj.shore.ui.task.TaskViewModel

/**
 * REFERENCES
 *
 * https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyNavHost.kt
 * https://developer.android.com/codelabs/jetpack-compose-navigation#3
 */

/**
 * Controller, handles routing logic
 * Passes Data Around
 */
@Composable
fun ShoreNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    /**
     * Authentication Logic
     */
    val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val shouldNavigateToHome by authViewModel.shouldNavigateToHome.collectAsState()
    val shouldRestartApp by authViewModel.shouldRestartApp.collectAsState()

    /**
     * For passing some data around
     */
    val taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)

    /**
     * Navigation Logic, defaults
     */
    val startDestination = when {
        shouldRestartApp -> {
            Login.route
        }
        shouldNavigateToHome -> {
            Home.route
        }
        else -> {
            Login.route
        }
    }

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
                HomeScreen(taskViewModel)
            }
        }
        composable(route = Task.route) { backStackEntry ->
            if (!shouldNavigateToHome) {
                navController.navigate(Login.route) {
                    popUpTo(Login.route)
                    launchSingleTop = true
                }
            } else {
                TaskScreen(
                    viewModel = taskViewModel,
                    onTaskClick = { task ->
                        taskViewModel.selectTask(task)
                        navController.navigate(TaskForm.route) {
                            popUpTo(Task.route)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
        composable(route = TaskForm.route) {
            TaskFormScreen(viewModel = taskViewModel)
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
