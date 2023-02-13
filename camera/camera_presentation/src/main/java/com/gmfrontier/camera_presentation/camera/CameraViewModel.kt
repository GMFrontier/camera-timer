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
import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core.domain.preferences.Preferences
import com.gmfrontier.core.domain.use_case.PlayCameraSound
import com.gmfrontier.core.util.UiEvent
import com.gmfrontier.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val playCameraSound: PlayCameraSound,
    private val preferences: Preferences,
    // private val handleCameraCaptures: HandleCameraCaptures
) : ViewModel() {

    var state by mutableStateOf(CameraState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _cameraEvent = Channel<CameraEvent>()
    val cameraEvent = _cameraEvent.receiveAsFlow()

    private var currentShotNumber = 0

    private var photoSession = Pair(1, state.numberOfShots)

    private val _showNumber = Channel<Int>()
    val showNumber = _showNumber.receiveAsFlow()

    private val _numberIsVisible = Channel<Boolean>()
    val numberIsVisible = _numberIsVisible.receiveAsFlow()

    private var photoSessionJob: Job = Job()
    private var countDownJob: MutableList<Job> = mutableListOf()
    private suspend fun onPhotoTaken(interval: Int) = viewModelScope.launch {
        countDownJob.add(
            launch {
                for (second in interval downTo 1) {
                    showCountDownTimer(second)
                }
            }
        )
        countDownJob.add(
            launch {
                for (second in interval downTo 1) {
                    playTimerSound(second)
                }
            }
        )
    }

    suspend fun playTimerSound(secondNumber: Int) {
        var delay: Int
        when (secondNumber) {
            2 -> {
                for (i in 0 until 4) {
                    playCameraSound.playTimerSound()
                    delay = 245
                    delay(delay.toDuration(DurationUnit.MILLISECONDS))
                }
            }
            1 -> {
                for (i in 0 until 8) {
                    playCameraSound.playTimerSound()
                    delay = 120
                    delay(delay.toDuration(DurationUnit.MILLISECONDS))
                }
            }
            else -> {
                playCameraSound.playTimerSound()
                delay = 1000
                delay(delay.toDuration(DurationUnit.MILLISECONDS))
            }
        }
    }

    suspend fun showCountDownTimer(secondNumber: Int) {
        _showNumber.send(secondNumber)
        _numberIsVisible.send(true)
        delay(500.toDuration(DurationUnit.MILLISECONDS))
        _numberIsVisible.send(false)
        delay(500.toDuration(DurationUnit.MILLISECONDS))
    }

    fun onEvent(event: CameraEvent) {
        when (event) {
            is CameraEvent.OnPictureTaken -> {
                viewModelScope.launch {
                    playCameraSound.playCameraShotSound()
                    async { if ((currentShotNumber == state.numberOfShots).not()) onPhotoTaken(state.shotsInterval) }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                                R.string.current_photo_session,
                                arrayOf(photoSession.first, state.numberOfShots)
                            )
                        )
                    )
                    repository.savePhoto(event.photo)
                        .onSuccess {
                            photoSession = photoSession.copy(first = photoSession.first + 1)
                        }
                        .onFailure {
                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.photo_repository_error)))
                        }
                }
            }
            CameraEvent.OnResume ->
                loadUserSettings()
            CameraEvent.OnSwitchCamera ->
                if (CameraSelector.DEFAULT_BACK_CAMERA == state.cameraMode) {
                    state = state.copy(
                        cameraMode = CameraSelector.DEFAULT_FRONT_CAMERA
                    )
                    preferences.saveCameraMode(CameraMode.FrontCamera)
                } else {
                    state = state.copy(
                        cameraMode = CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    preferences.saveCameraMode(CameraMode.BackCamera)
                }
            is CameraEvent.OnFlashSwitch ->
                if (event.isActive) {
                    state = state.copy(
                        flashMode = ImageCapture.FLASH_MODE_ON
                    )
                    preferences.saveFlashMode(ImageCapture.FLASH_MODE_ON)
                } else {
                    state = state.copy(
                        flashMode = ImageCapture.FLASH_MODE_OFF
                    )
                    preferences.saveFlashMode(ImageCapture.FLASH_MODE_OFF)
                }
            CameraEvent.OnStartPhotoSession -> {
                photoSessionJob = viewModelScope.launch {
                    state = state.copy(
                        isPhotoSessionActive = true
                    )
                    photoSession = photoSession.copy(first = 1)
                    async { onPhotoTaken(state.initialDelay) }
                    delay(state.initialDelay.toDuration(DurationUnit.SECONDS))
                    for (i in 1..state.numberOfShots) {
                        currentShotNumber = i
                        _cameraEvent.send(CameraEvent.TakePicture)
                        delay(state.shotsInterval.toDuration(DurationUnit.SECONDS))
                    }
                    state = state.copy(
                        isPhotoSessionActive = false
                    )
                }
            }
            CameraEvent.OnStopPhotoSession -> {
                viewModelScope.launch {
                    photoSessionJob.cancel()
                    _numberIsVisible.send(false)
                    countDownJob.forEach { it.cancel() }
                    state = state.copy(
                        isPhotoSessionActive = false
                    )
                }
            }
            else -> Unit
        }
    }

    private fun loadUserSettings() {
        val userSettings = preferences.loadUserSettings()
        val cameraMode =
            if (userSettings.cameraMode == CameraMode.BackCamera)
                CameraSelector.DEFAULT_BACK_CAMERA
            else
                CameraSelector.DEFAULT_FRONT_CAMERA
        state = state.copy(
            flashMode = userSettings.flashMode,
            cameraMode = cameraMode,
            numberOfShots = userSettings.numberOfShots,
            shotsInterval = userSettings.shotsInterval,
            initialDelay = userSettings.initialDelay,
        )
    }
}