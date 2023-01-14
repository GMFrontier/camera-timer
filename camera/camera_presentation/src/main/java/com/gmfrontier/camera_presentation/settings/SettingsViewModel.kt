package com.gmfrontier.camera_presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gmfrontier.core.domain.preferences.Preferences
import com.gmfrontier.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadUserSettings()
    }

    fun onEvent(event: SettingsEvent) {

    }

    private fun loadUserSettings() {
        val userSettings = preferences.loadUserSettings()
        state = state.copy(
            flashMode = userSettings.flashMode,
            cameraMode = userSettings.cameraMode,
            numberOfShots = userSettings.numberOfShots,
            shotsInterval = userSettings.shotsInterval,
            initialDelay = userSettings.initialDelay,
        )
    }
}