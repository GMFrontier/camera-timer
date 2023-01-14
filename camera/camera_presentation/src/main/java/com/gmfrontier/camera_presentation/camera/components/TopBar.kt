package com.gmfrontier.camera_presentation.camera.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmfrontier.camera_presentation.R
import com.gmfrontier.core.util.UiText

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onFlashClick: () -> Unit,
    onSettingsClick: () -> Unit,
    flashActive: Boolean
) {
    val isFlashActive = remember {
        mutableStateOf(flashActive)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        CircleButton(
            size = 30.dp,
            iconRes = R.drawable.ic_settings,
            onClick = onSettingsClick,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.settings),
            padding = 0.dp
        )
        IconTextItem(
            size = 30.dp,
            iconRes = R.drawable.ic_number_shots,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.number_shots),
            title = "1"
        )
        IconTextItem(
            size = 30.dp,
            iconRes = R.drawable.ic_shot_interval,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.shot_interval),
            title = "1s"
        )
        CircleButton(
            size = 30.dp,
            iconRes =
            if (isFlashActive.value)
                R.drawable.ic_flash_on
            else
                R.drawable.ic_flash_off,
            onClick = {
                isFlashActive.value = !isFlashActive.value
                onFlashClick()
            },
            contentDescription =
            if (isFlashActive.value)
                UiText.StringResource(com.gmfrontier.core.R.string.flash_on)
            else
                UiText.StringResource(
                    com.gmfrontier.core.R.string.flash_off
                ),
            padding = 5.dp,
            iconColor =
            if (isFlashActive.value)
                Color.Yellow
            else
                Color.White,
        )
    }
}