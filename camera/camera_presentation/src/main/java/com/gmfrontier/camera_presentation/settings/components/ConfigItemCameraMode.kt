package com.gmfrontier.camera_presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core_ui.LocalSpacing
import com.gmfrontier.core_ui.components.Spinner

@Composable
fun ConfigItemCameraMode(
    modifier: Modifier = Modifier,
    text: String,
    iconRes: Int,
    size: Dp = 24.dp,
    onClick: (cameraMode: CameraMode) -> Unit,
    selectedMode: CameraMode
) {
    val spacing = LocalSpacing.current
    val cameraMap : Map<Int, String> = CameraMode::class.sealedSubclasses
        .toSet()
        .map { it.objectInstance as CameraMode }
        .associate { it.nameRes to stringResource(id = it.nameRes) }
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

        Spinner(
            items = cameraMap.values.toList(),
            selectedItem = stringResource(id = selectedMode.nameRes),
            onItemSelected = { value ->
                onClick(CameraMode.fromInt(cameraMap.filterValues { it == value }.keys.first()))
            },
            selectedItemFactory = { modifier, item ->
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .wrapContentSize()
                ) {
                    Text(text = item)

                    Icon(
                        painter = painterResource(id = com.gmfrontier.core_ui.R.drawable.ic_arrow_drop_down),
                        contentDescription = "drop down arrow"
                    )
                }
            },
            dropdownItemFactory = { item, _ ->
                Text(text = item)
            }
        )
    }
}
