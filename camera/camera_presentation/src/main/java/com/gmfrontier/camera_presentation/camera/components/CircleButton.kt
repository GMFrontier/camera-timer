package com.gmfrontier.camera_presentation.camera.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmfrontier.core.util.UiText

@Composable
fun CircleButton(
    iconRes: Int,
    size: Dp = 60.dp,
    iconTint: Color = Color.White,
    onClick: () -> Unit,
    contentDescription: UiText,
    padding: Dp = 15.dp
) {
    val context = LocalContext.current
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.size(size),  //avoid the oval shape
        shape = CircleShape,
        contentPadding = PaddingValues(padding),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(contentColor = iconTint),
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription.asString(context)
        )
    }

}