package com.gmfrontier.camera_domain.di

import com.gmfrontier.camera_domain.repository.PhotoRepository
import com.gmfrontier.camera_domain.use_case.SavePhoto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CameraDomainModule {

    @Provides
    @Singleton
    fun provideSavePhotoUseCase(repository: PhotoRepository): SavePhoto {
        return SavePhoto(repository)
    }
}