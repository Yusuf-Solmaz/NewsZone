package com.yms.domain.model.user_preferences
import com.yms.domain.R
interface Displayable {
    val displayName: Int
}

enum class FollowUpTime(override val displayName: Int) : Displayable {
    EVERY_DAY(R.string.every_day),
    A_FEW_TIMES_A_WEEK(R.string.a_few_times_a_week),
    SOMETIMES(R.string.sometimes),
    VERY_RARELY(R.string.very_rarely)
}

enum class AgeGroup(override val displayName: Int) : Displayable {
    AGE_5_15(R.string.child_age),
    AGE_16_25(R.string.teen_age),
    AGE_26_35(R.string.adult_age),
    AGE_35_PLUS(R.string.senior_age)
}

enum class Gender(override val displayName: Int) : Displayable {
    FEMALE(R.string.female),
    MALE(R.string.male)
}