package com.gmfrontier.camera_presentation.camera.components

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmfrontier.camera_presentation.R
import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core.util.UiText

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onFlashClick: (isActive: Boolean) -> Unit,
    onSettingsClick: () -> Unit,
    flashActive: Boolean,
    numberOfShots : Int,
    shotsInterval : Int,
    initialDelay : Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        CircleButton(
            size = 50.dp,
            iconRes = R.drawable.ic_settings,
            onClick = onSettingsClick,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.settings),
            padding = 10.dp
        )
        IconTextItem(
            size = 30.dp,
            iconRes = R.drawable.ic_number_shots,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.number_shots),
            title = numberOfShots.toString()
        )
        IconTextItem(
            size = 30.dp,
            iconRes = R.drawable.ic_delay,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.initial_delay),
            title = stringResource(id = com.gmfrontier.core.R.string.initial_delay_value, initialDelay)
        )
        IconTextItem(
            size = 30.dp,
            iconRes = R.drawable.ic_shot_interval,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.shot_interval),
            title = stringResource(id = com.gmfrontier.core.R.string.shots_interval_value, shotsInterval)
        )
        CircleButton(
            size = 30.dp,
            iconRes =
            if (flashActive)
                R.drawable.ic_flash_on
            else
                R.drawable.ic_flash_off,
            onClick = {
                onFlashClick(!flashActive)
            },
            contentDescription =
            if (flashActive)
                UiText.StringResource(com.gmfrontier.core.R.string.flash_on)
            else
                UiText.StringResource(
                    com.gmfrontier.core.R.string.flash_off
                ),
            padding = 5.dp,
            iconTint =
            if (flashActive)
                Color.Yellow
            else
                Color.White,
        )
    }
}