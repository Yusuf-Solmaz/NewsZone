package com.yms.domain.repository.user_preferences

import com.yms.domain.model.user_preferences.UserPreferencesLanguage
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository{
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>

    suspend fun saveCategory(category: String)
    fun readCategory(): Flow<String>

    suspend fun saveDarkMode(isDarkMode: Boolean)
    fun readDarkMode(): Flow<Boolean>

    suspend fun saveLanguage(language: UserPreferencesLanguage)
    fun readLanguage(): Flow<String>


}