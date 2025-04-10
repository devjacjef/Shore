package com.jj.shore.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.data.task.Task
import com.jj.shore.ui.AppViewModelProvider

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val taskUiState: TaskUiState by viewModel.taskUiState.collectAsState()
    val taskList = taskUiState.taskList

    var activeTaskId by rememberSaveable { mutableStateOf<Int?>(null) }

    // TODO: Implement Selecting Tasks

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            items(items = taskList, key = { it.id }) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { activeTaskId = item.id }
                ) {
                    Text(text = item.title)
                    Text(text = item.description)
                }
            }
        }

        selectedTaskModal(
            activeTaskId = activeTaskId,
            onDismiss = { activeTaskId = null },
            taskList = taskList
        )

        Column(
            Modifier.align(Alignment.BottomStart)
        ) {

            // TODO: Implement a Press & Hold feature

            Button(
                onClick = {
                    // When the button is clicked, insert a new task
                    val newTask =
                        Task(title = "New Task", description = "Description for the new task")
                    viewModel.insertTask(newTask)
                },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }

            // TODO: Hide unless selectTasks is true
            Button(
                onClick = {
                    viewModel.clearAllTasks()
                },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Filled.Delete, "Add")
            }
        }
    }
}

@Composable
fun selectedTaskModal(
    activeTaskId: Int?,
    onDismiss: () -> Unit,
    taskList: List<Task>
) {
    if (activeTaskId != null) {
        val selectedTask = taskList.firstOrNull { it.id == activeTaskId }
        selectedTask?.let { task ->
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .padding(24.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    Text("Title: ${task.title}", style = MaterialTheme.typography.titleLarge)
                    Text("Description: ${task.description}")
                    Button(onClick = { onDismiss() }) {
                        Text("Close")
                    }
                }
            }
        }
    }
}