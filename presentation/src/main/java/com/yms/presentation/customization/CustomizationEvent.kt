package com.yms.presentation.customization

import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender

sealed interface CustomizationEvent{
    data class UpdateSelection(val followUpTime: FollowUpTime? = null, val ageGroup: AgeGroup? = null, val gender: Gender? = null) : CustomizationEvent
    data class SavePreferences(val onComplete: () -> Unit) : CustomizationEvent
}