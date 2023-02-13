package com.gmfrontier.camera_presentation.settings

import com.gmfrontier.core.domain.model.CameraMode

sealed class SettingsEvent {
    data class OnFlashModeChange(val flashMode: Boolean) : SettingsEvent()
    data class OnCameraModeChange(val cameraMode: CameraMode) : SettingsEvent()
    object OnNavigateUp : SettingsEvent()
}