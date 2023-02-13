package com.gmfrontier.camera_presentation.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmfrontier.camera_presentation.camera.components.CameraCapture
import com.gmfrontier.core.util.UiEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun CameraScreen(
    scaffoldState: ScaffoldState,
    viewModel: CameraViewModel = hiltViewModel(),
    navigateToGallery: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val context = LocalContext.current
    var job: Job = Job()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    job.cancel()
                    job = launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                else -> Unit
            }
        }
    }
    Box {
        CameraCapture(
            modifier = Modifier.fillMaxSize(),
            navigateToSettings = navigateToSettings,
            navigateToGallery = navigateToGallery,
        )
    }
}