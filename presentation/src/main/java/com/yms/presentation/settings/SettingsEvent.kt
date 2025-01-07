package com.yms.presentation.settings

import com.yms.domain.model.user_preferences.UserPreferencesLanguage

sealed interface SettingsEvent {
    data class SaveLanguage(val language: UserPreferencesLanguage) : SettingsEvent
    data class SaveDarkMode(val isDarkMode: Boolean) : SettingsEvent
}