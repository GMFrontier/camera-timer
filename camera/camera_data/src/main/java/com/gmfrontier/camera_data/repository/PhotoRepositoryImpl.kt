package com.gmfrontier.camera_data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.gmfrontier.camera_domain.repository.PhotoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class PhotoRepositoryImpl(
    @ApplicationContext private val context: Context
) : PhotoRepository {

    override suspend fun savePhoto(photo: File): Result<Uri> = withContext(Dispatchers.IO) {
        val filePath: String = photo.path
        val bitmap = BitmapFactory.decodeFile(filePath)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            saveImageInQ(bitmap)
        else
            saveImageInLegacy(bitmap)
    }

    private fun saveImageInLegacy(bitmap: Bitmap): Result<Uri> {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES +
                        File.separator +
                        PhotoRepository.APP_PHOTO_DIRECTORY
            )
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
        fos.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        return Result.success(image.toUri())
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageInQ(bitmap: Bitmap): Result<Uri> {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + PhotoRepository.APP_PHOTO_DIRECTORY
            )
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }
        val contentResolver = context.contentResolver
        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        imageUri?.let {
            contentValues.clear()
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
            contentResolver.update(it, contentValues, null, null)
            return Result.success(it)
        }
        return Result.failure(Throwable())
    }
}