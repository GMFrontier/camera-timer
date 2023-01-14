package com.gmfrontier.core.domain.preferences

import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core.domain.model.UserSettings

interface Preferences {
    fun saveFlashMode(flashMode: Int)
    fun saveCameraMode(cameraMode: CameraMode)
    fun saveNumberOfShots(numberOfShots: Int)
    fun saveShotsInterval(seconds: Int)
    fun saveInitialDelay(seconds: Int)

    fun loadUserSettings(): UserSettings

    companion object {
        const val PREFERENCES_NAME = "camera_shots_preferences"
        const val KEY_FLASH_MODE = "flash_mode"
        const val KEY_CAMERA_MODE = "camera_mode"
        const val KEY_NUMBER_OF_SHOTS = "number_of_shots"
        const val KEY_SHOTS_INTERVAL = "shots_interval"
        const val KEY_INITIAL_DELAY = "initial_delay"
    }
}