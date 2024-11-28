package eu.tutorials.learnconnectapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import eu.tutorials.learnconnectapp.pages.*
import eu.tutorials.learnconnectapp.viewmodel.CourseViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

@Composable
fun Navigation(modifier: Modifier = Modifier , authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        
        composable("signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        
        composable("home") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            HomePage(modifier, navController, authViewModel, courseViewModel)
        }
        
        composable("profile") {
            ProfilePage(modifier, authViewModel, navController)
        }
        
        composable("myCourses") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            MyCoursesPage(modifier, navController, courseViewModel)
        }
        
        composable(
            route = "videoPlayer/{videoUrl}",
            arguments = listOf(
                navArgument("videoUrl") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
            val decodedUrl = java.net.URLDecoder.decode(encodedUrl, "UTF-8")
            VideoPlayerScreen(videoUrl = decodedUrl, navController = navController)
        }
    }
}