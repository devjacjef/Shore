package com.jj.shore.data.task

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskRemoteDataSource: TaskRemoteDataSource) {
    fun getAllTasks(currentUserIdFlow: Flow<String?>): Flow<List<Task>> {
        return taskRemoteDataSource.getAllTasks(currentUserIdFlow)
    }

    suspend fun create(task: Task): String {
        return taskRemoteDataSource.create(task)
    }

    suspend fun update(task: Task) {
        taskRemoteDataSource.update(task)
    }

    suspend fun deleteTasks(tasks: Set<Task>) {
        taskRemoteDataSource.deleteTasks(tasks)
    }

    suspend fun delete(taskId: String) {
        taskRemoteDataSource.delete(taskId)
    }
}