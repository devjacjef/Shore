package com.jj.shore.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * REFERENCES
 * https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/ui/components/RallyTabRow.kt
 */

// Navigation Bar for the App
@Composable
fun BottomNavigationBar()
{
    // TODO: Display All Screens in a Row
    // TODO: Implement Navigation
}

// Navigation Tabs for the Navigation Bar
@Composable
fun NavigationTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
)
{
    // TODO: Implement Styling
    // TODO: Implement Icon
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100