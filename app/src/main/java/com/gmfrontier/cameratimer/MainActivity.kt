package com.gmfrontier.cameratimer

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmfrontier.camera_presentation.camera.CameraScreen
import com.gmfrontier.camera_presentation.settings.SettingsScreen
import com.gmfrontier.cameratimer.navigation.Route
import com.gmfrontier.cameratimer.ui.theme.CameraTimerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTimerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.CAMERA,
                        modifier = Modifier.padding(it)
                    ) {
                        composable(Route.CAMERA) {
                            CameraScreen(
                                scaffoldState = scaffoldState,
                                navigateToGallery = {
                                    val intent = Intent()
                                    intent.action = Intent.ACTION_VIEW
                                    intent.type = "image/*"
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                },
                                navigateToSettings = {
                                    navController.navigate(Route.SETTINGS)
                                }
                            )
                        }
                        composable(Route.SETTINGS) {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }

}