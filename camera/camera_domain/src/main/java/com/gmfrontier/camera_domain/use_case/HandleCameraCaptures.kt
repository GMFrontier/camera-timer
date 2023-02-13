package com.gmfrontier.camera_domain.use_case

import com.gmfrontier.core.domain.use_case.PlayCameraSound
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class HandleCameraCaptures(
    private val playCameraSound: PlayCameraSound,
) {

    private val _showNumber = Channel<Int>()
    val showNumber = _showNumber.receiveAsFlow()

    val numberIsVisible = Channel<Boolean>()

    /*suspend fun onPhotoTaken(interval: Int) {
        for(second in interval downTo 1) playTimerSound(second)
    }*/

    /*suspend fun playTimerSound(secondNumber: Int) {
        withContext(Dispatchers.IO) {
            _showNumber.send(secondNumber)
            numberIsVisible.send(true)
            when (secondNumber) {
                2 -> {
                    for (i in 0 until 4) {
                        playCameraSound(R.raw.camera_timer_beep)
                        delay(250.toDuration(DurationUnit.MILLISECONDS))
                        if(i==1) numberIsVisible.send(false)
                    }
                }
                1 -> {
                    for (i in 0 until 8) {
                        playCameraSound(R.raw.camera_timer_beep)
                        delay(125.toDuration(DurationUnit.MILLISECONDS))
                        if(i==3) numberIsVisible.send(false)
                    }
                }
                else -> {
                    playCameraSound(R.raw.camera_timer_beep)
                    delay(500.toDuration(DurationUnit.SECONDS))
                    numberIsVisible.send(false)
                    delay(500.toDuration(DurationUnit.SECONDS))
                }
            }
        }
    }*/
}