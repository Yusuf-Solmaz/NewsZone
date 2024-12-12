package com.yms.presentation.onboarding.viewmodel

sealed interface OnBoardingEvent {
    data object SaveAppEntry : OnBoardingEvent
}