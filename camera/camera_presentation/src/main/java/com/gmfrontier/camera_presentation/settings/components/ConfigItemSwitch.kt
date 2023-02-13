package com.gmfrontier.camera_presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmfrontier.core_ui.LocalSpacing

@Composable
fun ConfigItemSwitch(
    modifier: Modifier = Modifier,
    text: String,
    iconRes: Int,
    iconTint: Color,
    size: Dp = 24.dp,
    onClick: (isTrue: Boolean) -> Unit,
    value: Boolean
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.spaceSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(size),
            painter = painterResource(id = iconRes),
            contentDescription = "",
            tint = iconTint
        )
        Text(text = text)
        Switch(
            checked = value,
            onCheckedChange = {
                onClick(!value)
            },
        )
    }
}