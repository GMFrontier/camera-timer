package com.gmfrontier.camera_presentation.settings

import com.gmfrontier.core.domain.model.CameraMode

data class SettingsState(
    val flashMode: Int = 2,
    val cameraMode: CameraMode = CameraMode.BackCamera,
    val numberOfShots: String = "1",
    val shotsInterval: String = "1",
    val initialDelay: String = "0"
)