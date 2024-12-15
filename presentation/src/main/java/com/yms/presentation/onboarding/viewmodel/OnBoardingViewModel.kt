package com.yms.presentation.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.usecase.user_preferences.app_entry.UserPreferencesUseCase
import com.yms.presentation.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userPreferencesUseCase: UserPreferencesUseCase
) : ViewModel(){

    private companion object {
        const val STOP_TIME_MILLIS = 5_000L
        const val TAG = "OnBoardingViewModel"
    }

    fun onEvent(event: OnBoardingEvent){
        when(event){
            is OnBoardingEvent.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }

    fun saveAppEntry(){
        viewModelScope.launch {
            userPreferencesUseCase.saveAppEntry()
        }
    }

    val uiState: StateFlow<OnBoardingState> =
        userPreferencesUseCase.readAppEntry().map {
            appEntry ->
            Log.d(TAG, "App Entry: $appEntry")
            if (appEntry){
                OnBoardingState(isLoading = false,startDestination = NavigationGraph.NEWS_HOME.name,isSplashScreenVisible = false)
            }
            else{
                OnBoardingState(isLoading = false,startDestination = NavigationGraph.ONBOARDING_SCREEN.name,isSplashScreenVisible = false)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = OnBoardingState()
        )


}

data class OnBoardingState(
    val isLoading:Boolean = true,
    val startDestination: String = NavigationGraph.ONBOARDING_SCREEN.name,
    val isSplashScreenVisible: Boolean = true
)