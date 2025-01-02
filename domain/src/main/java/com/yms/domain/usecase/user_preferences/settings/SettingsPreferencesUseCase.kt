package com.yms.domain.usecase.user_preferences.settings

import com.yms.domain.model.user_preferences.UserPreferencesLanguage
import com.yms.domain.repository.user_preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

data class SettingsPreferencesUseCase(
    val readLanguage: ReadLanguage,
    val saveLanguage: SaveLanguage,
    val saveDarkMode: SaveDarkMode,
    val readDarkMode: ReadDarkMode
)

class ReadLanguage(private val userPreferencesRepository: UserPreferencesRepository){
    operator fun invoke(): Flow<String> = userPreferencesRepository.readLanguage()
}

class SaveLanguage(private val userPreferencesRepository: UserPreferencesRepository){
    suspend operator fun invoke(language: UserPreferencesLanguage) = userPreferencesRepository.saveLanguage(language)
}

class SaveDarkMode(private val userPreferencesRepository: UserPreferencesRepository){
    suspend operator fun invoke(isDarkMode: Boolean) = userPreferencesRepository.saveDarkMode(isDarkMode)
}

class ReadDarkMode(private val userPreferencesRepository: UserPreferencesRepository){
    operator fun invoke(): Flow<Boolean> = userPreferencesRepository.readDarkMode()
}