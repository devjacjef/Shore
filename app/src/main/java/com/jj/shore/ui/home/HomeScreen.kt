package com.jj.shore.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.jj.shore.ui.task.TaskViewModel

/**
 * REFERENCES
 *
 * https://developer.android.com/codelabs/jetpack-compose-navigation#5
 */

/**
 * Home Screen
 * For greeting the user and displaying amount of outstanding tasks
 */
@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel
) {
    val tasksCount by taskViewModel.outstandingTaskCount.collectAsState()

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
           Welcome();
            OutstandingPrompt(tasksCount)
        }
    }
}

/**
 * Welcome Prompt
 */
@Composable
fun Welcome() {
    Text("Welcome, back!", fontSize = 24.sp)
}

/**
 * Outstanding Prompt
 */
@Composable
fun OutstandingPrompt(outstanding: Int) {
    Text(
        text = buildAnnotatedString {
            append("You have ")

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            ) {
                append(outstanding.toString())
            }

            append(" outstanding tasks")
        },
        fontSize = 16.sp // Base size for the whole text
    )
}
