package eu.tutorials.learnconnectapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.tutorials.learnconnectapp.data.local.AppDatabase
import eu.tutorials.learnconnectapp.data.local.CourseDao
import eu.tutorials.learnconnectapp.data.model.Course
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import eu.tutorials.learnconnectapp.data.repository.CourseRepository

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val database = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                    val courseDao = database.courseDao()
                    
                    // Add sample courses with actual video URLs
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
        })
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.courseDao()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(
        courseDao: CourseDao,
        auth: FirebaseAuth
    ): CourseRepository {
        return CourseRepository(courseDao, auth)
    }
} 