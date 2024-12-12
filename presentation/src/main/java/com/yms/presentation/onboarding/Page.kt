package com.yms.presentation.onboarding

import androidx.annotation.StringRes
import com.yms.presentation.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int
)

fun getOnboardingPages(): List<Page> {
    return listOf(
        Page(
            title = R.string.onboarding_title_1,
            description = R.string.onboarding_description_1,
        ),
        Page(
            title = R.string.onboarding_title_2,
            description = R.string.onboarding_description_2,
        ),
        Page(
            title = R.string.onboarding_title_3,
            description = R.string.onboarding_description_3,
        )
    )
}