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
    private val authRepository: AuthRepository,
) : ViewModel() {

    val tasks = taskRepository.getAllTasks(authRepository.currentUserIdFlow)

    fun refreshTasks() {
        val userId = authRepository.currentUser?.uid
        if (!userId.isNullOrBlank()) {
            viewModelScope.launch {
                // Force refresh task collection
                taskRepository.refreshTasks(userId)
            }
        }
    }

    var selectedTask: Task? by mutableStateOf<Task?>(null)
        private set

    fun saveTask(task: Task) {
        val userId = authRepository.currentUser?.uid

        if (userId.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {
            val taskWithUser = task.copy(userId = userId)

            val taskToSave = taskWithUser

            if (taskToSave.id.isNullOrBlank()) {
                val newId = taskRepository.create(taskToSave)
                selectedTask = taskToSave.copy(id = newId)
            } else {
                taskRepository.update(taskToSave)
                selectedTask = taskToSave
            }
        }
    }

    fun markAsIncomplete(tasks: Set<Task>) {
        viewModelScope.launch {
            taskRepository.markAsIncomplete(tasks)
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



    fun markAsComplete(tasks: Set<Task>) {
        viewModelScope.launch {
            taskRepository.markAsComplete(tasks)
        }
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