package com.yms.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yms.presentation.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int
)

fun getOnboardingPages(): List<Page> {
    return listOf(
        Page(
            title = R.string.onboarding_title_1,
            description = R.string.onboarding_description_1,
            image = R.drawable.image_onboarding_1
        ),
        Page(
            title = R.string.onboarding_title_2,
            description = R.string.onboarding_description_2,
            image = R.drawable.image_onboarding_2
        ),
        Page(
            title = R.string.onboarding_title_3,
            description = R.string.onboarding_description_3,
            image = R.drawable.image_onboarding_3
        )
    )
}