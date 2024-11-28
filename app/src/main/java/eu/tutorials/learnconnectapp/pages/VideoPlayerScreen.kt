package eu.tutorials.learnconnectapp.pages

import TopBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    navController: NavHostController
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = "Video Player",
                showBackButton = true,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = ExoPlayer.Builder(context).build().apply {
                            setMediaItem(MediaItem.fromUri(videoUrl))
                            prepare()
                            playWhenReady = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}



