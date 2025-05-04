package com.jj.shore.ui.task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.shore.data.auth.AuthRepository
import com.jj.shore.data.task.Task
import com.jj.shore.data.task.TaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

/**
 * For controlling the logic between the Task Repo and the UI
 */
class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    /**
     * State for the list of tasks
     */
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    /**
     * State for the selected task
     */
    var selectedTask: Task? by mutableStateOf<Task?>(null)
        private set


    /**
     * State for the number of outstanding tasks
     */
    private val _outstandingTaskCount = MutableStateFlow(0)
    val outstandingTaskCount: StateFlow<Int> = _outstandingTaskCount

    /**
     * Initialize the ViewModel
     */
    init {

        viewModelScope.launch {
            collectTasksForUser(authRepository.currentUserIdFlow)
        }
    }

    /**
     * Collect task count for a specific user
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectTasksForUser(userIdFlow: Flow<String?>) {
        viewModelScope.launch {
            userIdFlow
                .filterNotNull()
                .flatMapLatest { userId ->
                    taskRepository.getAllTasks(flowOf(userId))
                }
                .collect { tasks ->
                    _tasks.value = tasks
                    _outstandingTaskCount.value = tasks.count { !it.completed }

                    Log.d("TaskViewModel", "Tasks updated: ${tasks.size} items, ${_outstandingTaskCount.value} outstanding")
                }
        }
    }


    /**
     * Update the task in the repository
     */
    fun saveTask(task: Task) {
        val userId = authRepository.currentUser?.uid

        if (userId.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {
            val taskWithUser = task.copy(userId = userId)

            if (taskWithUser.id.isNullOrBlank()) {
                val newId = taskRepository.create(taskWithUser)
                selectedTask = taskWithUser.copy(id = newId)
            } else {
                taskRepository.update(taskWithUser)
                selectedTask = taskWithUser
            }
        }
    }

    /**
     * Mark a task as Incomplete
     */
    fun markAsIncomplete(tasks: Set<Task>) {
        viewModelScope.launch {
            taskRepository.markAsIncomplete(tasks)
        }
    }

    /**
     * Create a new task
     */
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


    /**
     * Create a template task
     */
    fun createTemplateTask(): Task = Task(
        title = "Template Task",
        description = "This is a template task description.",
        completed = false, // Default to incomplete
        userId = authRepository.currentUser?.uid ?: "" // Ensure this matches your user structure
    )

    /**
     * Select a task
     */
    fun selectTask(task: Task?) {
        selectedTask = task
    }

    /**
     * Mark a task as Complete
     */
    fun markAsComplete(tasks: Set<Task>) {
        viewModelScope.launch {
            taskRepository.markAsComplete(tasks)
        }
    }

    /**
     * Delete tasks
     */
    fun deleteTasks(tasks: Set<Task>) {
        viewModelScope.launch {
            taskRepository.deleteTasks(tasks)
        }
    }
}