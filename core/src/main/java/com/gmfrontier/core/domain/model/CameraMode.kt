package com.gmfrontier.core.domain.model

import com.gmfrontier.core.R

sealed class CameraMode(val nameRes: Int) {
    object BackCamera : CameraMode(R.string.back_camera)
    object FrontCamera : CameraMode(R.string.front_camera)

    companion object {
        fun fromInt(nameRes: Int): CameraMode {
            return when(nameRes){
                R.string.back_camera -> BackCamera
                R.string.front_camera -> FrontCamera
                else -> BackCamera
            }
        }

    }
}
