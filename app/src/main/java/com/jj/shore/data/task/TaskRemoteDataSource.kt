package com.jj.shore.data.task

import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * REFERENCES:
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v2/app/src/main/java/com/google/firebase/example/makeitso/data/datasource/TodoListRemoteDataSource.kt#L7
 * https://firebase.google.com/docs/firestore/query-data/queries
 */

class TaskRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {


    /**
     * Listens to the current logged in user
     * then queries firestore for all tasks that belong to that user
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllTasks(currentUserIdFlow: Flow<String?>): Flow<List<Task>> {
        return currentUserIdFlow
            .onEach { Log.d("UserIdFlow", "Emitting userId: $it") }
            .filterNotNull()
            .flatMapLatest { userId ->
                Log.d("TaskRemoteDataSource", "Fetching tasks for userId: $userId")
                firestore.collection("tasks")
                    .whereEqualTo("userId", userId)
                    .dataObjects()
            }

    }

    /**
     * Creates a new task in firestore
     */
    suspend fun create(task: Task): String? {
        return try {
            val data = mapOf(
                "title" to task.title,
                "description" to task.description,
                "userId" to task.userId,
                "completed" to task.completed,
            )
            val result = firestore.collection("tasks").add(data).await()
            Log.d("TaskRemoteDataSource", "Task created with ID: ${result.id}")
            result.id
        } catch (e: Exception) {
            Log.e("TaskRemoteDataSource", "Failed to create task: ${e.message}", e)
            null
        }
    }

    /**
     * Updates an existing task in firestore
     */
    suspend fun update(task: Task) {
        val id = task.id
            ?: throw IllegalArgumentException("Task ID cannot be null or empty when updating")

        try {
            firestore.collection("tasks").document(id).set(task).await()
            Log.d("TaskRepository", "Task updated successfully.")
        } catch (e: Exception) {
            Log.e("TaskRepository", "Error updating task: ${e.message}", e)
        }

    }

    /**
     * Deletes multiple tasks from firestore
     */
    suspend fun deleteTasks(tasks: Set<Task>) {
        // Thing to let me perform multiple writes in a single operation
        val batch = firestore.batch()

        tasks.forEach { task ->
            task.id?.let { taskId ->
                val taskRef = firestore.collection("tasks").document(taskId)
                batch.delete(taskRef)
            }
        }

        batch.commit().await()
    }

    /**
     * Marks multiple tasks as complete in firestore
     */
    suspend fun markAsComplete(tasks: Set<Task>) {
        // Create a batch operation to update multiple documents at once
        val batch = firestore.batch()

        tasks.forEach { task ->
            task.id?.let { taskId ->
                val taskRef = firestore.collection("tasks").document(taskId)
                batch.update(taskRef, "completed", true) // Set 'completed' field to true
            }
        }

        batch.commit().await()
    }

    /**
     * Marks multiple tasks as incomplete in firestore
     */
    suspend fun markAsIncomplete(tasks: Set<Task>) {
        // Create a batch operation to update multiple documents at once
        val batch = firestore.batch()

        tasks.forEach { task ->
            task.id?.let { taskId ->
                val taskRef = firestore.collection("tasks").document(taskId)
                batch.update(taskRef, "completed", false) // Set 'completed' field to true
            }
        }

        batch.commit().await()
    }
}