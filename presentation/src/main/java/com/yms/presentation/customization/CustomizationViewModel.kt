package com.yms.presentation.customization

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomizationViewModel @Inject constructor(val customizationPreferencesUseCase: CustomizationPreferencesUseCase) : ViewModel() {

    private val _uiState = mutableStateOf(CustomizationUiState())
    val uiState: State<CustomizationUiState> = _uiState

    fun onEvent(event: CustomizationEvent) {
        when (event) {
            is CustomizationEvent.UpdateSelection -> updateSelection(event.followUpTime, event.ageGroup, event.gender)
            is CustomizationEvent.SavePreferences -> savePreferences(event.onComplete)
        }
    }

    private fun updateSelection(followUpTime: FollowUpTime? = null, ageGroup: AgeGroup? = null, gender: Gender? = null) {
        _uiState.value = _uiState.value.copy(
            followUpTimeSelection = followUpTime ?: _uiState.value.followUpTimeSelection,
            ageGroupSelection = ageGroup ?: _uiState.value.ageGroupSelection,
            genderSelection = gender ?: _uiState.value.genderSelection
        )
    }

    private fun savePreferences(onComplete: () -> Unit) {
        val followUpTime = _uiState.value.followUpTimeSelection
        val ageGroup = _uiState.value.ageGroupSelection
        val gender = _uiState.value.genderSelection

        if (followUpTime != null && ageGroup != null && gender != null) {
            viewModelScope.launch {
                customizationPreferencesUseCase.saveCategory(followUpTime, ageGroup, gender)
                onComplete()
            }
        }
    }

}

data class CustomizationUiState(
    val followUpTimeSelection: FollowUpTime? = null,
    val ageGroupSelection: AgeGroup? = null,
    val genderSelection: Gender? = null
)