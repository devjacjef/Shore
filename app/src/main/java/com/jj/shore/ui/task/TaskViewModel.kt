package com.jj.shore.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.jj.shore.data.task.Task
import com.jj.shore.data.task.TaskRepository
import com.jj.shore.helpers.connectivity.ConnectivityObserver
import com.jj.shore.ui.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _status = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _status.asStateFlow()

    init {
        viewModelScope.launch {
            connectivityObserver.observe().distinctUntilChanged().collect() { status ->
                _status.value = status
            }
        }
    }

    val taskUiState: StateFlow<TaskUiState> =
        taskRepository.getAllTasksStream().map { TaskUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TaskViewModel.TIMEOUT_MILLIS),
                initialValue = TaskUiState()
            )

    fun insertTask(task: com.jj.shore.data.task.Task) {
        viewModelScope.launch {
            taskRepository.insert(task) // You can call methods here!
        }
    }

    fun deleteTaskByIds(taskIds: List<Int>) {
        viewModelScope.launch {
            taskRepository.deleteTasksByIds(taskIds)
        }
    }

    fun clearAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TaskUiState(val taskList: List<Task> = listOf())