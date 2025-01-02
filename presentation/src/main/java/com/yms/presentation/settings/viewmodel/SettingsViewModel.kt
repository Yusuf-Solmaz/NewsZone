package com.yms.presentation.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.user_preferences.UserPreferencesLanguage
import com.yms.domain.usecase.user_preferences.settings.SettingsPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPreferencesUseCase: SettingsPreferencesUseCase
) : ViewModel() {

    private companion object {
        const val STOP_TIME_MILLIS = 5_000L
    }

    val languageState: StateFlow<LanguageState> =
        settingsPreferencesUseCase.readLanguage().map { language ->
            try {
                LanguageState(language = language)
            } catch (e: IllegalArgumentException) {
                LanguageState(language = UserPreferencesLanguage.ENGLISH.language,error = e.message)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = LanguageState()
        )

    val darkModeState: StateFlow<DarkModeState> =
        settingsPreferencesUseCase.readDarkMode().map { isDarkMode ->
            DarkModeState(isDarkMode = isDarkMode)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = DarkModeState()
        )

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SaveLanguage -> saveLanguage(event.language)
            is SettingsEvent.SaveDarkMode -> saveDarkMode(event.isDarkMode)
        }
    }

    private fun saveLanguage(language: UserPreferencesLanguage) {
        viewModelScope.launch {
            settingsPreferencesUseCase.saveLanguage(language)
        }
    }

    private fun saveDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsPreferencesUseCase.saveDarkMode(isDarkMode)
        }
    }
}

data class LanguageState(
    val language: String = "en",
    val error: String? = null
)

data class DarkModeState(
    val isDarkMode: Boolean = false,
    val error: String? = null
)

sealed interface SettingsEvent {
    data class SaveLanguage(val language: UserPreferencesLanguage) : SettingsEvent
    data class SaveDarkMode(val isDarkMode: Boolean) : SettingsEvent
}