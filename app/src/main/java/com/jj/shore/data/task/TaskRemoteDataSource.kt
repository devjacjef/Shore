package com.jj.shore.data.task

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * REFERENCES:
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v2/app/src/main/java/com/google/firebase/example/makeitso/data/datasource/TodoListRemoteDataSource.kt#L7
 */

class TaskRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    /**
     * Listens to the current logged in user
     * then queries firestore for all tasks that belong to that user
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllTasks(currentUserIdFlow: Flow<String?>): Flow<List<Task>> {

        return currentUserIdFlow.flatMapLatest { userId ->
            Log.d("TaskRemoteDataSource", "User ID for Firestore query: $userId")
            firestore.collection("tasks")
                .whereEqualTo("userId", userId)
                .dataObjects()
        }
    }

    suspend fun create(task: Task): String {
        val data = mapOf(
            "title" to task.title,
            "description" to task.description,
            "userId" to task.userId,
            "completed" to task.completed,
        )

        return firestore.collection("tasks").add(data).await().id
    }

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

    suspend fun delete(taskId: String) {
        firestore.collection("tasks").document(taskId).delete().await()
    }

    suspend fun deleteTasks(tasks: Set<Task>) {
        tasks.forEach { task ->
            task.id?.let { taskId ->
                firestore.collection("tasks").document(taskId).delete().await()
            }
        }
    }

    fun getOutstandingTaskCountFlow(userId: String): Flow<Int> = callbackFlow {
        val listenerRegistration = firestore.collection("tasks")
            .whereEqualTo("completed", false)
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                trySend(snapshot?.size() ?: 0)
            }

        awaitClose { listenerRegistration.remove() }
    }
}