package com.yms.presentation.navigation

import androidx.annotation.StringRes
import com.yms.presentation.R

enum class NavigationGraph(@StringRes val title: Int?){
    ONBOARDING_SCREEN(null),
    SPLASH_SCREEN(null),
    CUSTOMIZATION_SCREEN(null),
    NEWS_HOME(R.string.home)
}