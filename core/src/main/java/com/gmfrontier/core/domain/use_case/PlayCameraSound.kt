package com.gmfrontier.core.domain.use_case

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext

class PlayCameraSound(
    @ApplicationContext private val context: Context
) {

    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { reset() }
    }

    operator fun invoke(@RawRes soundRes: Int) {
        val assetFileDescriptor = context.resources.openRawResourceFd(soundRes) ?: return
        mediaPlayer.run {
            reset()
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.declaredLength
            )
            prepareAsync()
        }
    }
}