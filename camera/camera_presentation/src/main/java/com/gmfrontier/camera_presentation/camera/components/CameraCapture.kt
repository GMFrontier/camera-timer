package com.gmfrontier.camera_presentation.camera.components

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmfrontier.camera_presentation.camera.CameraEvent
import com.gmfrontier.camera_presentation.camera.CameraViewModel
import com.gmfrontier.camera_presentation.camera.extensions.executor
import com.gmfrontier.camera_presentation.camera.extensions.getCameraProvider
import com.gmfrontier.camera_presentation.camera.extensions.takePicture
import com.gmfrontier.camera_presentation.camera.util.Permission
import com.gmfrontier.core_ui.LocalSpacing
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val state = viewModel.state

    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "You said you wanted a picture, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        Box(modifier = modifier) {
            val lifecycleOwner = LocalLifecycleOwner.current
            val coroutineScope = rememberCoroutineScope()
            var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .setFlashMode(state.flashMode)
                        .build()
                )
            }
            Box {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onUseCase = {
                        previewUseCase = it
                    }
                )
            }
            TopBar(
                modifier = Modifier
                    .height(55.dp)
                    .alpha(.65f)
                    .background(
                        color = MaterialTheme.colors.background
                    )
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 20.dp),
                flashActive = state.flashMode == FLASH_MODE_ON,
                onFlashClick = { viewModel.onEvent(CameraEvent.SwitchFlash) },
                onSettingsClick = { viewModel.onEvent(CameraEvent.NavigateToSettings) }
            )
            BottomBar(
                modifier = Modifier
                    .height(125.dp)
                    .alpha(.65f)
                    .background(
                        color = MaterialTheme.colors.background
                    )
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp),
                onGallery = {
                    viewModel.onEvent(CameraEvent.NavigateToGallery)
                },
                onCameraShot = {
                    coroutineScope.launch {
                        imageCaptureUseCase.takePicture(context.executor, state.flashMode).let {
                            viewModel.onEvent(CameraEvent.TakePhoto)
                            viewModel.onEvent(CameraEvent.SavePhoto(it))
                        }
                    }
                },
                onSwitchCamera = {
                    viewModel.onEvent(CameraEvent.SwitchCamera)
                }
            )
            LaunchedEffect(previewUseCase, state.cameraMode) {
                val cameraProvider = context.getCameraProvider()
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, state.cameraMode, previewUseCase, imageCaptureUseCase
                    )
                } catch (ex: Exception) {
                    Log.e("CameraCapture", "Failed to bind camera use cases", ex)
                }
            }
        }
    }
}