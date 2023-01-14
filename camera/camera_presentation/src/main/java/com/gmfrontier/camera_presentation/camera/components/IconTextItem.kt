package com.gmfrontier.camera_presentation.camera.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gmfrontier.core.util.UiText

@Composable
fun IconTextItem(
    size: Dp,
    iconRes: Int,
    iconColor: Color = Color.White,
    contentDescription: UiText,
    title: String
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(size)
                .padding(0.dp),
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription.asString(context),
            tint = iconColor
        )
        Text(
            modifier = Modifier.padding(0.dp),
            text = title,
            fontSize = 10.sp
        )
    }
}