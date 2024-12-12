package com.yms.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.yms.data.repository.UserPreferencesRepositoryImpl.UserPreferences.APP_ENTRY
import com.yms.data.repository.UserPreferencesRepositoryImpl.UserPreferences.TAG
import com.yms.data.utils.APP_ENTRY_PREFERENCES
import com.yms.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor (private val dataStore: DataStore<Preferences>): UserPreferencesRepository {

    private object UserPreferences{
        val APP_ENTRY = booleanPreferencesKey(APP_ENTRY_PREFERENCES)
        const val TAG = "UserPreferencesRepository"
    }

    override suspend fun saveAppEntry() {
        Log.d("OnBoardingViewModel", "saveAppEntry() called")
        dataStore.edit { preferences ->
            preferences[APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    Log.e(TAG, "Error reading preferences.", it)
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[APP_ENTRY] == true
            }
    }
}