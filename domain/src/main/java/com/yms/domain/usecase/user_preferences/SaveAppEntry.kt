package com.yms.domain.usecase.user_preferences

import com.yms.domain.repository.UserPreferencesRepository

class SaveAppEntry (private val userPreferencesManager: UserPreferencesRepository){

    suspend operator fun invoke(){
        userPreferencesManager.saveAppEntry()
    }
}