package com.gmfrontier.core.domain.use_case

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.gmfrontier.core.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PlayCameraSound @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(
            AudioAttributes.USAGE_ASSISTANCE_SONIFICATION
        )
        .setContentType(
            AudioAttributes.CONTENT_TYPE_SONIFICATION
        )
        .build()

    private var soundPool : SoundPool = SoundPool.Builder()
        .setMaxStreams(2)
        .setAudioAttributes(audioAttributes)
        .build()
    private var cameraShotSound: Int = soundPool.load(context, R.raw.camera_shot, 1)
    private var timerSound: Int = soundPool.load(context, R.raw.camera_timer_beep, 1)

    fun playTimerSound() {
        soundPool.play(timerSound, 1f,1f,0,0,1f)
    }
    fun playCameraShotSound() {
        soundPool.play(cameraShotSound, 1f,1f,0,0,1f)
    }
}