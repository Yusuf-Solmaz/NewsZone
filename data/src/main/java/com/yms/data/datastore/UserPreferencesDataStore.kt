package com.yms.data.datastore

import android.app.UiModeManager
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yms.data.utils.APP_ENTRY_PREFERENCES
import com.yms.data.utils.CATEGORY_PREFERENCES
import com.yms.data.utils.DARK_MODE_PREFERENCES
import com.yms.data.utils.LANGUAGE_PREFERENCES
import com.yms.domain.model.user_preferences.UserPreferencesLanguage
import com.yms.domain.repository.user_preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val context: Context) : UserPreferencesRepository {

    private object UserPreferences {
        val APP_ENTRY = booleanPreferencesKey(APP_ENTRY_PREFERENCES)
        val CATEGORY = stringPreferencesKey(CATEGORY_PREFERENCES)
        val DARK_MODE = booleanPreferencesKey(DARK_MODE_PREFERENCES)
        val LANGUAGE = stringPreferencesKey(LANGUAGE_PREFERENCES)

        const val TAG = "UserPreferencesRepository"
    }

    private fun isSystemInDarkMode(): Boolean {
        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        return uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
    }

    private fun getSystemLanguage(): String {
        return context.resources.configuration.locales[0].language
    }

    override suspend fun saveDarkMode(isDarkMode: Boolean) {
        savePreference(UserPreferences.DARK_MODE, isDarkMode)
    }

    override fun readDarkMode(): Flow<Boolean> {
        val defaultDarkMode = isSystemInDarkMode()
        return readPreference(UserPreferences.DARK_MODE, defaultDarkMode)
    }

    override suspend fun saveLanguage(language: UserPreferencesLanguage) {
        savePreference(UserPreferences.LANGUAGE, language.language)
    }

    override fun readLanguage(): Flow<String> {
        val systemLanguage = getSystemLanguage()
        val defaultLanguage = when (systemLanguage) {
            "tr", "en" -> systemLanguage
            else -> "en"
        }
        return readPreference(UserPreferences.LANGUAGE, defaultLanguage)
    }


    override suspend fun saveAppEntry() {
        Log.d(UserPreferences.TAG, "saveAppEntry() called")
        savePreference(UserPreferences.APP_ENTRY, true)
    }

    override fun readAppEntry(): Flow<Boolean> {
        return readPreference(UserPreferences.APP_ENTRY, false)
    }

    override suspend fun saveCategory(category: String) {
        savePreference(UserPreferences.CATEGORY, category)
        Log.d(UserPreferences.TAG, "saveCategory() called with category: $category")
    }

    override fun readCategory(): Flow<String> {
        return readPreference(UserPreferences.CATEGORY, "general")
    }

    private suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun <T> readPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e(UserPreferences.TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }
}