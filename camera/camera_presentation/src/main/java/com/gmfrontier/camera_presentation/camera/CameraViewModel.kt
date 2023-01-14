package com.gmfrontier.camera_presentation.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmfrontier.camera_domain.repository.PhotoRepository
import com.gmfrontier.core.R
import com.gmfrontier.core.domain.use_case.PlayCameraSound
import com.gmfrontier.core.util.UiEvent
import com.gmfrontier.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val playCameraSound: PlayCameraSound
) : ViewModel() {

    private var photoFile: File? = null

    var state by mutableStateOf(CameraState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _cameraEvent = Channel<CameraEvent>()
    val cameraEvent = _cameraEvent.receiveAsFlow()

    fun onEvent(event: CameraEvent) {
        when (event) {
            is CameraEvent.SavePhoto -> {
                viewModelScope.launch {
                    repository.savePhoto(event.photo)
                        .onSuccess {
                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.photo_saved)))
                        }
                        .onFailure {
                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.photo_repository_error)))
                        }
                }
            }
            CameraEvent.TakePhoto -> {
                playCameraSound(R.raw.camera_shot)
            }

            CameraEvent.NavigateToGallery -> {
                viewModelScope.launch {
                    _cameraEvent.send(CameraEvent.NavigateToGallery)
                }
            }
            CameraEvent.NavigateToSettings ->
                viewModelScope.launch {
                    _cameraEvent.send(CameraEvent.NavigateToSettings)
                }
            CameraEvent.SwitchCamera ->
                state = state.copy(
                    cameraMode =
                    if (CameraSelector.DEFAULT_BACK_CAMERA == state.cameraMode)
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    else
                        CameraSelector.DEFAULT_BACK_CAMERA,
                )
            CameraEvent.SwitchFlash ->
                state = state.copy(
                    flashMode =
                    if (state.flashMode == ImageCapture.FLASH_MODE_ON)
                        ImageCapture.FLASH_MODE_OFF
                    else
                        ImageCapture.FLASH_MODE_ON
                )
        }
    }
}