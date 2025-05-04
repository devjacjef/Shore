    package com.jj.shore.data.task

    import android.util.Log
    import kotlinx.coroutines.flow.Flow
    import javax.inject.Inject

    class TaskRepository @Inject constructor(private val taskRemoteDataSource: TaskRemoteDataSource) {
        fun getAllTasks(currentUserId: Flow<String?>): Flow<List<Task>> {
            return taskRemoteDataSource.getAllTasks(currentUserId)
        }

        suspend fun create(task: Task): String? {
            Log.d("Create task called", "Create task called again again")
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
    }