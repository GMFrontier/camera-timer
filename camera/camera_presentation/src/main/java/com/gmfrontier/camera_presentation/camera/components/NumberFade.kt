package com.gmfrontier.camera_presentation.camera.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*

@Composable
fun NumberFade(
    modifier: Modifier = Modifier,
    number: Int,
    fontSize: TextUnit = 14.sp,
    visible: Boolean
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(
            text = number.toString(),
            fontSize = fontSize
        )
    }
}