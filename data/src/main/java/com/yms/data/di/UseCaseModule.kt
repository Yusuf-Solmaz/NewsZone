package com.yms.data.di

import com.yms.domain.repository.UserPreferencesRepository
import com.yms.domain.usecase.user_preferences.ReadAppEntry
import com.yms.domain.usecase.user_preferences.SaveAppEntry
import com.yms.domain.usecase.user_preferences.UserPreferencesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUserPreferencesUseCase(userPreferencesManager: UserPreferencesRepository): UserPreferencesUseCase {
        return UserPreferencesUseCase(
            saveAppEntry = SaveAppEntry(userPreferencesManager),
            readAppEntry = ReadAppEntry(userPreferencesManager)
        )
    }
}