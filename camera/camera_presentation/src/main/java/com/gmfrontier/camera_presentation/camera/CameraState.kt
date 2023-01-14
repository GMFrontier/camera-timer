package com.gmfrontier.camera_presentation.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import java.io.File

data class CameraState(
    val photoFile: File? = null,
    val cameraMode: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    val flashMode: Int = ImageCapture.FLASH_MODE_OFF,
    val numberOfShots: Int = 1,
    val delayInSec: Int = 0,
    val intervalInSec: Int = 1
)