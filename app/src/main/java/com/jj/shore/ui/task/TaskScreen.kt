package com.jj.shore.ui.task

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    viewModel: TaskViewModel,
) {
    /**
     * State for the list of tasks
     */
    val tasks by viewModel.tasks.collectAsState()

    /**
     * State for the selected tasks
     */
    var selectedTasks by rememberSaveable { mutableStateOf<Set<Task>>(emptySet()) }

    /**
     * The task list
     */
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

        LazyColumn(

            verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
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

        /**
         * Action Buttons
         */
        Column(
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskActionButtons(
                viewModel,
                selectedTasks,
                OnClearSelection = { selectedTasks = setOf() })
        }
    }
}

/**
 * Menu for different operations
 */
@Composable
fun TaskActionButtons(
    viewModel: TaskViewModel,
    selectedTasks: Set<Task>,
    OnClearSelection: () -> Unit,
) {

    if (selectedTasks.isNotEmpty()) {
        val isAllCompleted = selectedTasks.all { it.completed }

        Button(
            onClick = {
                if (isAllCompleted) {
                    viewModel.markAsIncomplete(selectedTasks)
                } else {
                    viewModel.markAsComplete(selectedTasks)
                }
                OnClearSelection()
            },
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = if (isAllCompleted) Icons.Filled.Close else Icons.Filled.Check,
                contentDescription = if (isAllCompleted) "Incomplete" else "Complete",
                modifier = Modifier.size(32.dp)
            )
        }
    }

    // Delete Button
    if (selectedTasks.isNotEmpty()) {
        Button(
            onClick = {
                viewModel.deleteTasks(selectedTasks)
                OnClearSelection()
            },
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(32.dp) // Now this works!
            )
        }
    }

    // Create New Task Button
        Button(
            onClick = {
                val newTask = viewModel.createTemplateTask()
                viewModel.createTask(newTask)
                Log.d("Create task called", "Create task called")
            },
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier.size(32.dp)
            )
        }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    selected: Boolean = false,
    onTaskClick: (Task) -> Unit,
    onLongClick: (Task) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when {
                selected -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                task.completed -> MaterialTheme.colorScheme.inversePrimary
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onTaskClick(task)
                    Log.d("TaskCardGesture", "Clicked on ${task.title}")
                },
                onLongClick = {
                    onLongClick(task)
                    Log.d("TaskCardGesture", "Long-pressed on ${task.title}")
                }
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = task.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )

            task.id?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}
