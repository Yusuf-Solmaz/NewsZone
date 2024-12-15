package com.yms.domain.usecase.user_preferences.app_entry

import com.yms.domain.repository.user_preferences.UserPreferencesRepository

class SaveAppEntry (private val userPreferencesRepository: UserPreferencesRepository){

    suspend operator fun invoke(){
        userPreferencesRepository.saveAppEntry()
    }
}