package com.gmfrontier.camera_domain.use_case

import com.gmfrontier.camera_domain.repository.PhotoRepository
import java.io.File

class SavePhoto(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(photo: File) {
        repository.savePhoto(photo)
    }
}