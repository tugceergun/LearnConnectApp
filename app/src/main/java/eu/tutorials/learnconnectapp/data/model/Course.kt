package eu.tutorials.learnconnectapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val videoUrl: String,
    val isEnrolled: Boolean = false,
    val userId: String? = null
)