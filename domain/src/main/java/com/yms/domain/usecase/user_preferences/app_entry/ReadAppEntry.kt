package com.yms.domain.usecase.user_preferences.app_entry

import com.yms.domain.repository.user_preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return userPreferencesRepository.readAppEntry()
    }
}