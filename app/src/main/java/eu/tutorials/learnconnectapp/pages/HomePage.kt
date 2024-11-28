package eu.tutorials.learnconnectapp.pages

import BottomNavBar
import TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tutorials.learnconnectapp.AuthViewModel
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
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    courseViewModel: CourseViewModel
) {
    val courses by courseViewModel.courses.observeAsState(initial = emptyList())
    val context = LocalContext.current

    // Add LaunchedEffect to log courses for debugging
    LaunchedEffect(Unit) {
        println("Current courses: ${courses.size}")
    }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = "home"
            )
        }
    ) { paddingValues ->
        if (courses.isEmpty()) {
            // Show loading or empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(courses) { course ->
                    CourseCard(
                        course = course,
                        onClick = {
                            if (course.isEnrolled) {
                                val encodedUrl = java.net.URLEncoder.encode(course.videoUrl, "UTF-8")
                                navController.navigate("videoPlayer/$encodedUrl")
                            } else {
                                courseViewModel.enrollInCourse(course)
                            }
                        }
                    )
                }
            }
        }
    }
}
