package com.jj.shore.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String,
    val email: String,
    val name: String,
    // Don't need subcollections in here, added later when storing/fetching
)
