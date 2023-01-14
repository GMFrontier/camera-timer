package com.gmfrontier.camera_presentation.camera.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmfrontier.camera_presentation.R
import com.gmfrontier.core.util.UiText
import com.gmfrontier.core_ui.LocalSpacing

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onGallery: () -> Unit,
    onCameraShot: () -> Unit,
    onSwitchCamera: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        CircleButton(
            iconRes = R.drawable.ic_gallery,
            onClick = onGallery,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.gallery)
        )
        CircleButton(
            iconRes = R.drawable.ic_camera_shot,
            size = 80.dp,
            onClick = onCameraShot,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.camera_shot)
        )
        CircleButton(
            iconRes = R.drawable.ic_switch_camera,
            onClick = onSwitchCamera,
            contentDescription = UiText.StringResource(com.gmfrontier.core.R.string.swtich_camera)
        )
    }
}