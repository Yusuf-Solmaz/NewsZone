package com.yms.data.di

import com.yms.domain.repository.CustomizationRepository
import com.yms.domain.repository.UserPreferencesRepository
import com.yms.domain.usecase.user_preferences.app_entry.ReadAppEntry
import com.yms.domain.usecase.user_preferences.app_entry.SaveAppEntry
import com.yms.domain.usecase.user_preferences.app_entry.UserPreferencesUseCase
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import com.yms.domain.usecase.user_preferences.category.SaveCategory
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
    fun provideUserPreferencesUseCase(userPreferencesRepository: UserPreferencesRepository): UserPreferencesUseCase {
        return UserPreferencesUseCase(
            saveAppEntry = SaveAppEntry(userPreferencesRepository),
            readAppEntry = ReadAppEntry(userPreferencesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCustomizationPreferencesUseCase(customizationRepository: CustomizationRepository): CustomizationPreferencesUseCase {
        return CustomizationPreferencesUseCase(SaveCategory(customizationRepository))
    }
}