package com.gmfrontier.camera_presentation.camera.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.gmfrontier.camera_presentation.R
import com.gmfrontier.camera_presentation.camera.CameraEvent
import com.gmfrontier.camera_presentation.camera.CameraViewModel
import com.gmfrontier.camera_presentation.camera.extensions.executor
import com.gmfrontier.camera_presentation.camera.extensions.getCameraProvider
import com.gmfrontier.camera_presentation.camera.extensions.takePicture
import com.gmfrontier.camera_presentation.camera.util.Permission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.*
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToGallery: () -> Unit,
) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = stringResource(R.string.camera_permission_rationale),
        permissionNotAvailableContent = {
            Column(modifier) {
                Text(stringResource(R.string.camera_not_found))
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }) {
                    Text(stringResource(R.string.open_settings))
                }
            }
        }
    ) {
        Box(modifier = modifier) {
            val state = viewModel.state
            val coroutineScope = rememberCoroutineScope()
            var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }
            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_RESUME ->
                            viewModel.onEvent(CameraEvent.OnResume)
                        else -> Unit
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }
            var number by remember {
                mutableStateOf(0)
            }
            LaunchedEffect(true) {
                viewModel.cameraEvent.collect { event ->
                    when (event) {
                        is CameraEvent.TakePicture -> {
                            takePicture(
                                coroutineScope = coroutineScope,
                                imageCaptureUseCase = imageCaptureUseCase,
                                context = context,
                                flashMode = viewModel.state.flashMode,
                                onTakePicture = { file ->
                                    viewModel.onEvent(CameraEvent.OnPictureTaken(file))
                                }
                            )
                        }
                        is CameraEvent.ShowCounter -> {
                            number = event.number
                        }
                        else -> Unit
                    }
                }
            }
            Box {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onUseCase = {
                        previewUseCase = it
                    }
                )
                NumberFade(
                    modifier = Modifier
                        .align(Alignment.Center),
                    number = viewModel.showNumber.collectAsState(0).value,
                    fontSize = 52.sp,
                    visible = viewModel.numberIsVisible.collectAsState(false).value
                )
            }
            TopBar(
                modifier = Modifier
                    .alpha(.65f)
                    .background(
                        color = MaterialTheme.colors.background
                    )
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 20.dp),
                flashActive = state.flashMode == FLASH_MODE_ON,
                numberOfShots = state.numberOfShots,
                shotsInterval = state.shotsInterval,
                initialDelay = state.initialDelay,
                onFlashClick = { viewModel.onEvent(CameraEvent.OnFlashSwitch(it)) },
                onSettingsClick = {
                    viewModel.onEvent(CameraEvent.OnStopPhotoSession)
                    navigateToSettings()
                }
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
                onGallery = navigateToGallery,
                onStartCameraShot = {
                    viewModel.onEvent(CameraEvent.OnStartPhotoSession)
                },
                onSwitchCamera = {
                    viewModel.onEvent(CameraEvent.OnSwitchCamera)
                },
                isPhotoSessionActive = state.isPhotoSessionActive,
                onStopCameraShot = {
                    viewModel.onEvent(CameraEvent.OnStopPhotoSession)
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

private fun takePicture(
    coroutineScope: CoroutineScope,
    imageCaptureUseCase: ImageCapture,
    context: Context,
    flashMode: Int,
    onTakePicture: (File) -> Unit
) {
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            imageCaptureUseCase.takePicture(context.executor, flashMode).let {
                onTakePicture(it)
            }
        }
    }
}