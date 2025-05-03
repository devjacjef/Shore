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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _outstandingTaskCount = MutableStateFlow(0)
    val outstandingTaskCount: StateFlow<Int> = _outstandingTaskCount

    init {
        Log.d("TaskViewModel", "Initializing with user: ${authRepository.currentUser?.uid}")

        viewModelScope.launch {
            authRepository.currentUserIdFlow.collect { userId ->
                Log.d("TaskViewModel", "User ID flow updated: $userId")

                if (userId != null) {
                    collectTasksForUser(userId)
                } else {
                    // ✅ Reset task state on sign-out
                    _tasks.value = emptyList()
                    _outstandingTaskCount.value = 0
                }
            }
        }
    }

    fun forceRefresh(userId: String) {
        collectTasksForUser(userId)
    }


    private fun collectTasksForUser(userId: String) {
        viewModelScope.launch {
            taskRepository.getAllTasksForUser(userId).collect { allTasks ->
                val userTasks = allTasks.filter { it.userId == userId }
                _tasks.value = userTasks
                _outstandingTaskCount.value = userTasks.count { !it.completed }

                Log.d("TaskViewModel", "Tasks updated: ${userTasks.size} items")
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
        val userId = authRepository.currentUser?.uid
        if (userId.isNullOrBlank()) {
            Log.d("TaskViewModel", "User ID is null or blank.")
            return
        }

        viewModelScope.launch {
            val taskWithUser = task.copy(userId = userId)
            taskRepository.create(taskWithUser)
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