package com.gmfrontier.camera_data.di

import android.app.Application
import com.gmfrontier.camera_data.repository.PhotoRepositoryImpl
import com.gmfrontier.camera_domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraDataModule {

    @Provides
    @Singleton
    fun providePhotoRepository(application: Application): PhotoRepository {
        return PhotoRepositoryImpl(application)
    }
}