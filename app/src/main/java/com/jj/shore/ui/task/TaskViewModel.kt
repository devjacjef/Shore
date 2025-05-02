package com.jj.shore.ui.task

import android.app.ActivityManager.TaskDescription
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.jj.shore.data.auth.AuthRepository
import com.jj.shore.data.task.Task
import com.jj.shore.data.task.TaskRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var isCompleted by mutableStateOf(false)
        private set

    fun toggleCompleted() {
        isCompleted = !isCompleted
        Log.d("TaskViewModel", "Toggled isCompleted: $isCompleted")
    }

    val tasks = taskRepository.getAllTasks(authRepository.currentUserIdFlow)

    var selectedTask: Task? by mutableStateOf<Task?>(null)
        private set

    fun saveTask(task: Task) {
        val userId = authRepository.currentUser?.uid

        if(userId.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {
            val taskWithUser = task.copy(userId = userId)

            val taskToSave = taskWithUser.copy(completed = isCompleted)

            if (taskToSave.id.isNullOrBlank()) {
                // Creating a new task
                val newId = taskRepository.create(taskToSave)
                selectedTask = taskToSave.copy(id = newId)
                Log.d("TaskViewModel", "Created new task with ID: $newId, $isCompleted")
            } else {
                // Updating an existing task
                taskRepository.update(taskWithUser)
                selectedTask = taskWithUser
                Log.d("TaskViewModel", "Updated task with ID: ${task.id} , $isCompleted")
            }
        }
    }

    fun createTask(task: Task) {
        viewModelScope.launch {
            taskRepository.create(task)
        }
    }

    fun createTemplateTask(): Task = Task(
        title = "Template Task",
        description = "This is a template task description.",
        completed = false, // Default to incomplete
        userId = authRepository.currentUser?.uid ?: "" // Ensure this matches your user structure
    )

    fun selectTask(task: Task?) {
        selectedTask = task
    }

    fun delete(taskId: String) {
        viewModelScope.launch {
            taskRepository.delete(taskId)
        }
    }

    fun deleteTasks(tasks: Set<Task>) {
        viewModelScope.launch {
            taskRepository.deleteTasks(tasks)
        }
    }
}