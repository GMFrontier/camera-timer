package com.gmfrontier.camera_presentation.camera

import java.io.File

sealed class CameraEvent {
    object TakePhoto : CameraEvent()
    object SwitchFlash : CameraEvent()
    object SwitchCamera : CameraEvent()
    data class SavePhoto(val photo: File): CameraEvent()
    object NavigateToGallery : CameraEvent()
    object NavigateToSettings : CameraEvent()
}