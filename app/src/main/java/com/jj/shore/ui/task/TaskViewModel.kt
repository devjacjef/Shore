package com.jj.shore.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.jj.shore.data.task.Task

class TaskViewModel(
    private val firestoreRepository: FirebaseFirestore
) : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        firestoreRepository.collection("tasks")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("Firestore", "Listen failed", e)
                    return@addSnapshotListener
                }

                if(snapshot != null) {
                    Log.d("Firestore", "Current data: ${snapshot.documents}")
                    val task = snapshot.toObjects(Task::class.java)
                    _tasks.value = task
                }

            }
    }
}