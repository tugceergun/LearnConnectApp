package eu.tutorials.learnconnectapp.data.repository

import eu.tutorials.learnconnectapp.data.local.CourseDao
import eu.tutorials.learnconnectapp.data.model.Course
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth

class CourseRepository @Inject constructor(
    private val courseDao: CourseDao,
    private val auth: FirebaseAuth
) {
    fun getAllCourses(): Flow<List<Course>> {
        val userId = auth.currentUser?.uid
        return courseDao.getAllCoursesForUser(userId ?: "")
    }

    suspend fun enrollInCourse(course: Course) {
        val userId = auth.currentUser?.uid
        courseDao.updateCourse(course.copy(isEnrolled = true, userId = userId))
    }

    suspend fun initializeData() {
        if (courseDao.getCourseCount() == 0) {
            val sampleCourses = listOf(
                Course(
                    id = "1",
                    title = "Android Development Basics",
                    description = "Learn the fundamentals of Android development with Kotlin",
                    videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
                    isEnrolled = false
                ),
                Course(
                    id = "2",
                    title = "Advanced Android Concepts",
                    description = "Master advanced Android development techniques",
                    videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp4",
                    isEnrolled = false
                ),
                Course(
                    id = "3",
                    title = "Jetpack Compose Masterclass",
                    description = "Build modern Android UIs with Jetpack Compose",
                    videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/TearsOfSteel.mp4",
                    isEnrolled = false
                )
            )
            courseDao.insertCourses(sampleCourses)
        }
    }
}