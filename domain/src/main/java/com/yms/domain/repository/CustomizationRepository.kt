package com.yms.domain.repository

import com.yms.domain.model.user_preferences_model.AgeGroup
import com.yms.domain.model.user_preferences_model.FollowUpTime
import com.yms.domain.model.user_preferences_model.Gender

interface CustomizationRepository {
    suspend fun saveCategory(followUpTime: FollowUpTime, ageGroup: AgeGroup, gender: Gender)

}