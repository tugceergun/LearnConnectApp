package eu.tutorials.learnconnectapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import eu.tutorials.learnconnectapp.data.model.Course
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses WHERE userId IS NULL OR userId = :userId")
    fun getAllCoursesForUser(userId: String): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourses(courses: List<Course>)

    @Update
    suspend fun updateCourse(course: Course)

    @Query("SELECT COUNT(*) FROM courses")
    suspend fun getCourseCount(): Int
}