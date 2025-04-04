package com.jj.shore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jj.shore.ui.home.HomeScreen
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
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen()
        }
        composable(route = Task.route) {
            TaskScreen()
        }
    }

    // TODO: Implement NavHost
    // TODO: Add Routes
}

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

// TODO: Import Dependencies
// TODO: Implement this feature...
//fun NavHostController.navigateSingleTopTo(route: String) =
//    this.navigate(route) {
//        // Pop up to the start destination of the graph to
//        // avoid building up a large stack of destinations
//        // on the back stack as users select items
//        popUpTo(
//            this@navigateSingleTopTo.graph.findStartDestination().id
//        ) {
//            saveState = true
//        }
//        // Avoid multiple copies of the same destination when
//        // reselecting the same item
//        launchSingleTop = true
//        // Restore state when reselecting a previously selected item
//        restoreState = true
//    }