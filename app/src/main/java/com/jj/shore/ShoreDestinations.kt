package com.jj.shore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.jj.shore.ui.home.HomeScreen
import com.jj.shore.ui.task.TaskScreen

/**
 * REFERENCES
 *
 * https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyDestinations.kt
 */

/**
 * Provides a interface for a Destination.
 */
sealed interface ShoreDestination {
    val icon: ImageVector
    val route: String
    val screen: @Composable () -> Unit
}

/**
 * Home Destination
 */
data object Home : ShoreDestination {
    override val icon = Icons.Filled.Home
    override val route = "home"
    override val screen: @Composable () -> Unit = { HomeScreen() }
}

/**
 * Destination for Task Screen
 */
data object Task : ShoreDestination {
    override val icon = Icons.Filled.DateRange
    override val route = "task"
    override val screen: @Composable () -> Unit = { TaskScreen() }
}

/**
 * List of routes for the navigation bar
 */
val navigationScreens = listOf(Home, Task);

// TODO: Implement Routes for Tasks
// TODO: Implement Routes for Chores
// TODO: Implement Routes for Rewards

/**
 * Example code to be referenced later in the project...
 */
//data object SingleAccount : RallyDestination {
//    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
//    // part of the RallyTabRow selection
//    override val icon = Icons.Filled.Money
//    override val route = "single_account"
//    const val accountTypeArg = "account_type"
//    val routeWithArgs = "$route/{$accountTypeArg}"
//    val arguments = listOf(
//        navArgument(accountTypeArg) { type = NavType.StringType }
//    )
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
//    )
//}