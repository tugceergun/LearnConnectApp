package eu.tutorials.learnconnectapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.tutorials.learnconnectapp.data.model.Course

@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}