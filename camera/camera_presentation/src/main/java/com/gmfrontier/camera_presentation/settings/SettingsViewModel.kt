package com.gmfrontier.camera_presentation.settings

import androidx.camera.core.ImageCapture
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmfrontier.camera_presentation.R
import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core.domain.preferences.Preferences
import com.gmfrontier.core.domain.use_case.FilterOutDigits
import com.gmfrontier.core.util.UiEvent
import com.gmfrontier.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadUserSettings()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnCameraModeChange -> onCameraModeEnter(event.cameraMode)
            is SettingsEvent.OnFlashModeChange -> onFlashModeEnter(event.flashMode)
            SettingsEvent.OnNavigateUp -> {
                saveUserPreferences()
            }
        }
    }

    private fun saveUserPreferences() {
        val numberOfShots = state.numberOfShots.toIntOrNull() ?: kotlin.run {
            1
        }
        val initialDelay = state.initialDelay.toIntOrNull() ?: kotlin.run {
            0
        }
        val shotsInterval = state.shotsInterval.toIntOrNull() ?: kotlin.run {
            1
        }
        preferences.saveCameraMode(state.cameraMode)
        preferences.saveFlashMode(state.flashMode)
        preferences.saveNumberOfShots(numberOfShots)
        preferences.saveInitialDelay(initialDelay)
        preferences.saveShotsInterval(shotsInterval)
        viewModelScope.launch { _uiEvent.send(UiEvent.NavigateUp) }
    }

    private fun onCameraModeEnter(value: CameraMode) {
        state = state.copy(
            cameraMode = value
        )
    }

    private fun onFlashModeEnter(value: Boolean) {
        state = if (value) {
            state.copy(
                flashMode = ImageCapture.FLASH_MODE_ON
            )
        } else {
            state.copy(
                flashMode = ImageCapture.FLASH_MODE_OFF
            )
        }
    }

    fun onNumberOfShotsEnter(value: String) {
        if (value.length <= 3) {
            state = state.copy(
                numberOfShots = filterOutDigits(value)
            )
        }
    }

    fun onInitialDelayEnter(value: String) {
        if (value.length <= 3) {
            state = state.copy(
                initialDelay = filterOutDigits(value)
            )
        }
    }

    fun onShotsIntervalEnter(value: String) {
        if (value.length <= 3) {
            state = state.copy(
                shotsInterval = filterOutDigits(value)
            )
        }
    }

    private fun loadUserSettings() {
        val userSettings = preferences.loadUserSettings()
        state = state.copy(
            flashMode = userSettings.flashMode,
            cameraMode = userSettings.cameraMode,
            numberOfShots = userSettings.numberOfShots.toString(),
            shotsInterval = userSettings.shotsInterval.toString(),
            initialDelay = userSettings.initialDelay.toString(),
        )
    }
}