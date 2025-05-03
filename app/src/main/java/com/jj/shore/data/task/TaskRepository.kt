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

    suspend fun markAsComplete(tasks: Set<Task>) {
        taskRemoteDataSource.markAsComplete(tasks)
    }

    suspend fun markAsIncomplete(tasks: Set<Task>) {
        taskRemoteDataSource.markAsIncomplete(tasks)
    }

    suspend fun delete(taskId: String) {
        taskRemoteDataSource.delete(taskId)
    }

    suspend fun getOutstandingTasks(): Flow<List<Task>> {
        return taskRemoteDataSource.getOutstandingTasks()
    }

    fun getOutstandingTaskCountFlow(userId: String): Flow<Int> {
        return taskRemoteDataSource.getOutstandingTaskCountFlow(userId)
    }

    suspend fun getTasksCount(): Int {
        return taskRemoteDataSource.getTasksCount()
    }
}