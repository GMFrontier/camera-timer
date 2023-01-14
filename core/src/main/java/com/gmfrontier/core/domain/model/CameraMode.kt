package com.gmfrontier.core.domain.model

sealed class CameraMode(val name: String) {
    object BackCamera : CameraMode("back")
    object FrontCamera : CameraMode("front")

    companion object {
        fun fromString(name: String): CameraMode {
            return when(name){
                "back" -> BackCamera
                "front" -> FrontCamera
                else -> BackCamera
            }
        }
    }
}
