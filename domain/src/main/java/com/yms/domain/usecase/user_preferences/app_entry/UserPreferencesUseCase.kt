package com.yms.domain.usecase.user_preferences.app_entry

data class UserPreferencesUseCase(
    val saveAppEntry: SaveAppEntry,
    val readAppEntry: ReadAppEntry
)