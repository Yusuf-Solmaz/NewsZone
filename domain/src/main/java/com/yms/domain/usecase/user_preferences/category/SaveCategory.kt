package com.yms.domain.usecase.user_preferences.category

import com.yms.domain.model.user_preferences_model.AgeGroup
import com.yms.domain.model.user_preferences_model.FollowUpTime
import com.yms.domain.model.user_preferences_model.Gender
import com.yms.domain.repository.CustomizationRepository

class SaveCategory (
    private val customizationRepository: CustomizationRepository){

    suspend operator fun invoke(followUpTime: FollowUpTime, ageGroup: AgeGroup, gender: Gender){
        customizationRepository.saveCategory(followUpTime, ageGroup, gender)
    }
}