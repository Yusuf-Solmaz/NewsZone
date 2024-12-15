package com.yms.presentation.customization.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.user_preferences_model.AgeGroup
import com.yms.domain.model.user_preferences_model.FollowUpTime
import com.yms.domain.model.user_preferences_model.Gender
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomizationViewModel @Inject constructor(val customizationPreferencesUseCase: CustomizationPreferencesUseCase) : ViewModel() {

    private val _uiState = mutableStateOf(CustomizationUiState())
    val uiState: State<CustomizationUiState> = _uiState

    fun updateSelection(followUpTime: FollowUpTime? = null, ageGroup: AgeGroup? = null, gender: Gender? = null) {
        _uiState.value = _uiState.value.copy(
            followUpTimeSelection = followUpTime ?: _uiState.value.followUpTimeSelection,
            ageGroupSelection = ageGroup ?: _uiState.value.ageGroupSelection,
            genderSelection = gender ?: _uiState.value.genderSelection
        )
    }

    fun savePreferences(onComplete: () -> Unit) {
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