package com.gmfrontier.camera_domain.repository

import android.net.Uri
import java.io.File

interface PhotoRepository {

    fun savePhoto(photo: File): Result<Uri>

    companion object {
        const val APP_PHOTO_DIRECTORY = "Camera Shots"
    }
}