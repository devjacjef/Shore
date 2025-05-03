package com.jj.shore.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.register.RegisterViewModel
import com.jj.shore.ui.task.TaskViewModel

/**
 * REFERENCES
 *
 * https://developer.android.com/codelabs/jetpack-compose-navigation#5
 *
 * NOTE:
 * Can add on click events if needed.
 */

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val email by viewModel.userEmail
    val tasksCount by taskViewModel.outstandingTaskCount.collectAsState()
    val userId by viewModel.userIdStateFlow.collectAsState()

    LaunchedEffect(email) {
        email?.let {
            if (userId != null) {
                taskViewModel.forceRefresh(userId!!)
            }
        }
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            email?.let { Welcome(it) } ?: Text("No email available")
            OutstandingPrompt(tasksCount)
        }
    }
}



@Composable
fun Welcome(email: String) {
    Text("Welcome, back!", fontSize = 24.sp)
}

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
