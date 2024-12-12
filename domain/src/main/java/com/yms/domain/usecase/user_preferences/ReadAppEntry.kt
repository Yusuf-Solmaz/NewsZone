package com.yms.domain.usecase.user_preferences

import com.yms.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val userPreferencesManager: UserPreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return userPreferencesManager.readAppEntry()
    }
}