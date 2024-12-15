package com.yms.domain.usecase.user_preferences.category

import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender
import com.yms.domain.repository.user_preferences.CustomizationRepository

class SaveCategory (
    private val customizationRepository: CustomizationRepository){

    suspend operator fun invoke(followUpTime: FollowUpTime, ageGroup: AgeGroup, gender: Gender){
        customizationRepository.saveCategory(followUpTime, ageGroup, gender)
    }
}