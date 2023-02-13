package com.gmfrontier.camera_presentation.camera

import java.io.File

sealed class CameraEvent {
    data class OnFlashSwitch(val isActive: Boolean) : CameraEvent()
    object OnSwitchCamera : CameraEvent()
    data class OnPictureTaken(val photo: File): CameraEvent()
    object OnResume : CameraEvent()
    object OnStartPhotoSession : CameraEvent()
    object TakePicture : CameraEvent()
    data class  ShowCounter(val number: Int) : CameraEvent()
    object OnStopPhotoSession : CameraEvent()
}