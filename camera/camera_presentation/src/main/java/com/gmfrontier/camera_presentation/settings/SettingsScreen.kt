package com.gmfrontier.camera_presentation.settings

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmfrontier.camera_presentation.R
import com.gmfrontier.camera_presentation.settings.components.ConfigItemCameraMode
import com.gmfrontier.camera_presentation.settings.components.ConfigItemInput
import com.gmfrontier.camera_presentation.settings.components.ConfigItemSwitch
import com.gmfrontier.core.domain.model.CameraMode
import com.gmfrontier.core.util.UiEvent
import com.gmfrontier.core_ui.LocalSpacing

@Composable
fun SettingsScreen(
    scaffoldState: ScaffoldState,
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val state = viewModel.state
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> navigateUp()
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceSmall, top = spacing.spaceLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        navigateUp()
                    }
                    .padding(spacing.spaceExtraSmall)
                    .weight(.8f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .alignBy(LastBaseline),
                text = stringResource(id = com.gmfrontier.core.R.string.settings),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.weight(1.8f))
        }
        Spacer(
            modifier = Modifier
                .height(spacing.spaceLarge)
        )
        ConfigItemCameraMode(
            text = stringResource(id = com.gmfrontier.core.R.string.camera_mode),
            iconRes =
            if(state.cameraMode == CameraMode.BackCamera)
                R.drawable.ic_camera_back
            else
                R.drawable.ic_camera_front,
            onClick = {
                viewModel.onEvent(SettingsEvent.OnCameraModeChange(it))
            },
            selectedMode = state.cameraMode
        )
        ConfigItemSwitch(
            modifier = Modifier,
            text = stringResource(id = com.gmfrontier.core.R.string.flash_mode),
            iconRes = if (viewModel.state.flashMode == ImageCapture.FLASH_MODE_ON)
                R.drawable.ic_flash_on
            else
                R.drawable.ic_flash_off,
            onClick = {
                viewModel.onEvent(SettingsEvent.OnFlashModeChange(it))
            },
            value = viewModel.state.flashMode == ImageCapture.FLASH_MODE_ON,
            iconTint =
            if (viewModel.state.flashMode == ImageCapture.FLASH_MODE_ON)
                Color.Yellow
            else
                Color.White,
        )
        ConfigItemInput(
            modifier = Modifier,
            text = stringResource(id = com.gmfrontier.core.R.string.number_shots),
            iconRes = R.drawable.ic_number_shots,
            onValueChange = viewModel::onNumberOfShotsEnter,
            inputValue = state.numberOfShots
        )
        ConfigItemInput(
            modifier = Modifier,
            text = stringResource(id = com.gmfrontier.core.R.string.initial_delay),
            iconRes = R.drawable.ic_delay,
            onValueChange = viewModel::onInitialDelayEnter,
            inputValue = state.initialDelay
        )
        ConfigItemInput(
            modifier = Modifier,
            text = stringResource(id = com.gmfrontier.core.R.string.shot_interval),
            iconRes = R.drawable.ic_shot_interval,
            onValueChange = viewModel::onShotsIntervalEnter,
            inputValue = state.shotsInterval
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { viewModel.onEvent(SettingsEvent.OnNavigateUp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.spaceExtraLarge, vertical = spacing.spaceLarge)
        ) {
            Text(text = stringResource(id = com.gmfrontier.core.R.string.save_changes))
        }
        /*Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { *//*TODO*//* }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = com.gmfrontier.core.R.string.about),
                    textAlign = TextAlign.Start,
                )
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { *//*TODO*//* },
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = com.gmfrontier.core.R.string.privacy_policy),
                    textAlign = TextAlign.Start,
                )
            }
        }*/
    }
}