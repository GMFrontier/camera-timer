package com.gmfrontier.core.domain.model

data class UserSettings(
    val flashMode: Int,
    val cameraMode: CameraMode,
    val numberOfShots: Int,
    val shotsInterval: Int,
    val initialDelay: Int
)