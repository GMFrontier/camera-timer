package com.gmfrontier.camera_presentation.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.gmfrontier.camera_presentation.camera.components.CameraCapture
import com.gmfrontier.core.util.UiEvent

@Composable
fun CameraScreen(
    scaffoldState: ScaffoldState,
    viewModel: CameraViewModel = hiltViewModel(),
    navigateToGallery: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.cameraEvent.collect { event ->
            when (event) {
                CameraEvent.NavigateToGallery -> navigateToGallery()
                CameraEvent.NavigateToSettings -> navigateToSettings()
                else -> Unit
            }
        }
    }
    Box {
        CameraCapture(
            modifier = Modifier.fillMaxSize(),
        )
    }
}