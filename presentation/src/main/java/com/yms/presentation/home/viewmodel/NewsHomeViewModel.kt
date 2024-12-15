package com.yms.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsHomeViewModel @Inject constructor(val customizationPreferencesUseCase: CustomizationPreferencesUseCase) :
    ViewModel() {

    private companion object {
        const val STOP_TIME_MILLIS = 5_000L
        const val TAG = "NewsHomeViewModel"
    }

    val categoryState: StateFlow<CategoryState> =
        customizationPreferencesUseCase.readCategory().map {
            category ->
            CategoryState(category = category, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = CategoryState()
        )

}


data class CategoryState(
    val category: String = "",
    val isLoading: Boolean = true
)