package com.gmfrontier.cameratimer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gmfrontier.core.domain.preferences.Preferences
import com.gmfrontier.core.domain.use_case.PlayCameraSound
import com.gmfrontier.core.data.preferences.DefaultPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesPlayCameraSoundUseCase(context: Application) : PlayCameraSound {
        return PlayCameraSound(context)
    }
}