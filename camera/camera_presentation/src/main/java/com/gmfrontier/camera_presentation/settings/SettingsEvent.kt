package com.gmfrontier.camera_presentation.settings

import com.gmfrontier.core.domain.model.CameraMode

sealed class SettingsEvent {
    data class OnFlashModeChange(val flashMode: Int) : SettingsEvent()
    data class OnCameraModeChange(val cameraMode: CameraMode) : SettingsEvent()
    data class OnNumberOfShotsChange(val numberOfShots: Int) : SettingsEvent()
    data class OnShotsIntervalChange(val shotsInterval: Int) : SettingsEvent()
    data class OnInitialDelayChange(val initialDelay: Int) : SettingsEvent()
    object OnNavigateUp : SettingsEvent()
}