package com.jj.shore.ui.task

import android.widget.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jj.shore.data.task.Task

@Composable
fun TaskFormScreen(
    viewModel: TaskViewModel
) {
    val selectedTask = viewModel.selectedTask

    var title by rememberSaveable { mutableStateOf(selectedTask?.title ?: "") }
    var description by rememberSaveable { mutableStateOf(selectedTask?.description ?: "") }
    var isCompleted by rememberSaveable { mutableStateOf(selectedTask?.completed ?: false) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Task Title") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.size(16.dp))

            // Description input (larger height)
            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Task Description") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(120.dp), // Taller for more lines
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                singleLine = false, // Enable multiline
                maxLines = 5
            )

            Spacer(Modifier.size(16.dp))

            // Completion toggle
            Button(
                onClick = { isCompleted = !isCompleted },
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp)
            ) {
                Text(if (isCompleted) "Mark Incomplete" else "Mark Complete")
            }

            Spacer(Modifier.size(16.dp))

            // Save button
            Button(
                onClick = {
                    val updatedTask = selectedTask?.copy(
                        title = title,
                        description = description,
                        completed = isCompleted
                    )
                    updatedTask?.let { viewModel.saveTask(it) }
                },
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp)
            ) {
                Text("Save Task")
            }
        }
    }
}
