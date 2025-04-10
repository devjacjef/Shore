package com.jj.shore.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * REFERENCES
 *
 * https://developer.android.com/codelabs/jetpack-compose-navigation#5
 *
 * NOTE:
 * Can add on click events if needed.
 */

@Composable
fun HomeScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Potato")
    }
}
