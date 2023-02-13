package com.gmfrontier.core.data.preferences

import android.content.SharedPreferences
import com.gmfrontier.core.R
import com.gmfrontier.core.domain.model.UserSettings
import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPref: SharedPreferences
) : Preferences {
    override fun saveFlashMode(flashMode: Int) {
        sharedPref.edit().putInt(Preferences.KEY_FLASH_MODE, flashMode).apply()
    }

    override fun saveCameraMode(cameraMode: CameraMode) {
        sharedPref.edit().putInt(Preferences.KEY_CAMERA_MODE, cameraMode.nameRes).apply()
    }

    override fun saveNumberOfShots(numberOfShots: Int) {
        sharedPref.edit().putInt(Preferences.KEY_NUMBER_OF_SHOTS, numberOfShots).apply()
    }

    override fun saveShotsInterval(seconds: Int) {
        sharedPref.edit().putInt(Preferences.KEY_SHOTS_INTERVAL, seconds).apply()
    }

    override fun saveInitialDelay(seconds: Int) {
        sharedPref.edit().putInt(Preferences.KEY_INITIAL_DELAY, seconds).apply()
    }

    override fun loadUserSettings(): UserSettings {
        val flashMode = sharedPref.getInt(Preferences.KEY_FLASH_MODE, 2)
        val cameraMode = sharedPref.getInt(Preferences.KEY_CAMERA_MODE, R.string.back_camera)
        val numberOfShots = sharedPref.getInt(Preferences.KEY_NUMBER_OF_SHOTS, 1)
        val shotsInterval = sharedPref.getInt(Preferences.KEY_SHOTS_INTERVAL, 1)
        val initialDelay = sharedPref.getInt(Preferences.KEY_INITIAL_DELAY, 1)
        return UserSettings(
            flashMode = flashMode,
            cameraMode = CameraMode.fromInt(cameraMode),
            numberOfShots = numberOfShots,
            shotsInterval = shotsInterval,
            initialDelay = initialDelay
        )
    }
}