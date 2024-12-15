package com.yms.domain.repository.user_preferences

import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender

interface CustomizationRepository {
    suspend fun saveCategory(followUpTime: FollowUpTime, ageGroup: AgeGroup, gender: Gender)

}