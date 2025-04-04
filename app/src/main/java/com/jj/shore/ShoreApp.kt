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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jj.shore.ui.components.BottomNavigationBar
import com.jj.shore.ui.theme.ShoreTheme

/**
 *  REFERENCES
 *  https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyActivity.kt
 *  https://developer.android.com/codelabs/jetpack-compose-navigation#3
 */

// FIXME: Remove this suppression later
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShoreApp() {
    val currentScreen: ShoreDestination by remember { mutableStateOf(Home) }
    // TODO: Research NavController
    val navController = rememberNavController()

    ShoreTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    allScreens = navigationScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen

                )
                // TODO: Add "Bottom Bar"
                // TODO: Add Bottom Navigation Bar
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                // TODO: Add the current screen
            }
        }
    }


    // TODO: Add Nav Controller
    // TODO: Add Back Stack
    // TODO: Add Current Destination
    // TODO: Add Current Screen
}

//@Preview
//@Composable
//fun ShoreAppPreview() {
//    ShoreTheme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize()
//        ) { innerPadding ->
//            ShoreApp(modifier = Modifier.padding(innerPadding))
//        }
//    }
//}