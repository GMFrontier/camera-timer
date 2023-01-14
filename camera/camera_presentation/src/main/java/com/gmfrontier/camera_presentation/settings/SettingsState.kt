package com.gmfrontier.camera_presentation.settings

import com.gmfrontier.core.domain.model.CameraMode

data class SettingsState(
    val flashMode: Int = 0,
    val cameraMode: CameraMode = CameraMode.BackCamera,
    val numberOfShots: Int = 1,
    val shotsInterval: Int = 1,
    val initialDelay: Int = 0
)