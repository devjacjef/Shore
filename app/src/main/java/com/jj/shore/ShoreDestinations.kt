package com.jj.shore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.jj.shore.ui.home.HomeScreen

/**
 * REFERENCES
 *
 * https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyDestinations.kt
 */

/**
 * PERSONAL NOTE
 * FIXME: REMOVE THIS ON SUBMISSION/MERGE
 * THIS IS MY ROUTES FOR THE APP,
 * THINK SIMILARLY TO LARAVEL ROUTES
 */

/**
 * Provides a interface for a Destination.
 */
sealed interface ShoreDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Defines Routes for the App
 */
data object Home : ShoreDestination {
    override val icon = Icons.Filled.Home
    override val route = "home"
}

data object Task : ShoreDestination {
    override val icon = Icons.Filled.DateRange
    override val route = "task"
}

data object Login : ShoreDestination {
    override val icon = Icons.Filled.AccountCircle
    override val route = "auth"
}

data object Settings : ShoreDestination {
    override val icon = Icons.Filled.Settings
    override val route = "settings"
}

data object Register : ShoreDestination {
    override val icon = Icons.Filled.AccountCircle
    override val route = "register"
}

data object TaskForm : ShoreDestination {
    override val icon = Icons.Filled.DateRange
    override val route = "taskform"
}

/**
 * List of places to navigate
 */
val navigationScreens = listOf(Home, Task, Settings);

// TODO: Implement Routes for Tasks
// TODO: Implement Routes for Chores
// TODO: Implement Routes for Rewards