package com.yms.domain.usecase.user_preferences

data class UserPreferencesUseCase(
    val saveAppEntry: SaveAppEntry,
    val readAppEntry: ReadAppEntry
)