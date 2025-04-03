package com.jj.shore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jj.shore.ui.theme.ShoreTheme

/**
 *  REFERENCES
 *  https://github.com/android/codelab-android-compose/blob/end/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyActivity.kt
 */

@Composable
fun ShoreApp(
    modifier: Modifier = Modifier,
) {
    // TODO: Add Nav Controller
    // TODO: Add Back Stack
    // TODO: Add Current Destination
    // TODO: Add Current Screen
}

@Preview
@Composable
fun ShoreAppPreview() {
    ShoreTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize()) {
            innerPadding ->
            ShoreApp(modifier = Modifier.padding(innerPadding))
        }
    }
}