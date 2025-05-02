package com.jj.shore.ui.task

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.data.task.Task
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.task.TaskViewModel

@Composable
fun TaskScreen(
    onTaskClick: (Task?) -> Unit,
    viewModel: TaskViewModel
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())

    var selectMode by rememberSaveable { mutableStateOf(false) }
    var selectedTasks by rememberSaveable { mutableStateOf<Set<Task>>(emptySet()) }

    var activeTask by rememberSaveable { mutableStateOf<Task?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

        LazyColumn {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    selected = selectedTasks.contains(task),
                    onTaskClick = { clickedTask ->
                            onTaskClick(clickedTask)
                    },
                    onLongClick = { longPressedTask ->
                        selectedTasks = if (selectedTasks.contains(longPressedTask)) {
                            selectedTasks - longPressedTask // Remove if already selected
                        } else {
                            selectedTasks + longPressedTask // Add if not selected
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.Transparent)
        ) {
            TaskActionButtons(
                viewModel,
                selectedTasks,
                OnClearSelection = { selectedTasks = setOf() })
        }
    }
}

@Composable
fun TaskActionButtons(
    viewModel: TaskViewModel,
    selectedTasks: Set<Task>,
    OnClearSelection: () -> Unit
) {

    // Delete Button
    if (selectedTasks.isNotEmpty()) {
        Button(
            onClick = {
                viewModel.deleteTasks(selectedTasks)
                OnClearSelection()
            },
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Filled.Delete, "Add")
        }
    }

    // Insert New Task Button
    Button(
        onClick = {
            // When the button is clicked, insert a new task
            val newTask = viewModel.createTemplateTask()
            viewModel.createTask(newTask)
        },
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.error)
    ) {
        Icon(Icons.Filled.Add, "Add")
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    selected: Boolean = false,
    onTaskClick: (Task) -> Unit,
    onLongClick: (Task) -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    onTaskClick(task)
                    Log.d("TaskCardGesture", "Clicked on ${task.title}")
                },
                onLongClick = {
                    onLongClick(task)  // Toggle selection on long press
                    Log.d("TaskCardGesture", "Long-pressed on ${task.title}")
                }
            )

    ) {
        Column(Modifier.background(if (selected) Color.LightGray else if (task.completed) Color.Green else Color.White)) {
            task.id?.let { Text(text = it) }
            Text(text = task.title)
            Text(text = task.description)
            Text(text = task.completed.toString())
        }
    }
}
