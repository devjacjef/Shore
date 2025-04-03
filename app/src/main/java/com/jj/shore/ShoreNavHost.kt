package com.jj.shore

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * REFERENCES
 *
 * https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyNavHost.kt
 */

@Composable
fun ShoreNavHost(
//  navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // TODO: Implement NavHost
    // TODO: Add Routes
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