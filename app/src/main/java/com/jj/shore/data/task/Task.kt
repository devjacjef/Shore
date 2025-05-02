package com.jj.shore.data.task

import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentId

/**
 * REFERENCES
 * https://github.com/FirebaseExtended/make-it-so-android/blob/main/v2/app/src/main/java/com/google/firebase/example/makeitso/data/model/TodoItem.kt
 */

data class Task(
    @DocumentId val id: String? = "",
    val title: String = "",
    val description: String = "",
    val userId: String = "",
    val completed: Boolean = false
)
