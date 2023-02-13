package com.gmfrontier.camera_presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmfrontier.core_ui.LocalSpacing

@Composable
fun ConfigItemInput(
    modifier: Modifier = Modifier,
    text: String,
    iconRes: Int,
    size: Dp = 24.dp,
    onValueChange: (value: String) -> Unit,
    inputValue: String
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
        )
        Text(text = text)
        TextField(
            modifier = Modifier
                .width(64.dp)
                .padding(vertical = spacing.spaceExtraSmall),
            value = inputValue,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colors.onBackground
            ),
        )
    }
}