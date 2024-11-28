package eu.tutorials.learnconnectapp.pages

import TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tutorials.learnconnectapp.components.CourseCard
import eu.tutorials.learnconnectapp.viewmodel.CourseViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCoursesPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    courseViewModel: CourseViewModel
) {
    Scaffold(
        topBar = {
            TopBar(
                showBackButton = true,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        val courses by courseViewModel.courses.observeAsState(emptyList())
        val enrolledCourses = courses.filter { it.isEnrolled }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(enrolledCourses) { course ->
                CourseCard(
                    course = course,
                    onClick = { 
                        val encodedUrl = java.net.URLEncoder.encode(course.videoUrl, "UTF-8")
                        navController.navigate("videoPlayer/$encodedUrl")
                    }
                )
            }
        }
    }
} 