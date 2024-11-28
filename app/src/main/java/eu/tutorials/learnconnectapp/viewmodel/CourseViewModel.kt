package eu.tutorials.learnconnectapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.learnconnectapp.data.model.Course
import eu.tutorials.learnconnectapp.data.repository.CourseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: CourseRepository
)  : ViewModel() {
    init {
        viewModelScope.launch {
            repository.initializeData()
        }
    }

    val courses: LiveData<List<Course>> = repository.getAllCourses().asLiveData()

    fun enrollInCourse(course: Course) {
        viewModelScope.launch {
            repository.enrollInCourse(course)
        }
    }
}