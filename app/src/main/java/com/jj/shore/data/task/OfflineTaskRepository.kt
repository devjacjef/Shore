package com.jj.shore.data.task

import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTaskStream(id: Int): Flow<Task?> = taskDao.getTaskById(id)

    override suspend fun insert(task: Task) = taskDao.insert(task)

    override suspend fun delete(task: Task) = taskDao.delete(task)

    override suspend fun update(task: Task) = taskDao.update(task)

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()  // Call to delete all tasks
    }
}