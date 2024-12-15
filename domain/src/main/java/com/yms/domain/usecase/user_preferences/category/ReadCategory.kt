package com.yms.domain.usecase.user_preferences.category

import com.yms.domain.repository.user_preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ReadCategory(private val userPreferencesRepository: UserPreferencesRepository){
    operator fun invoke(): Flow<String> {
        return userPreferencesRepository.readCategory()
    }
}