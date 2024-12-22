package com.yms.domain.model.user_preferences

interface Displayable {
    val displayName: String
}

enum class FollowUpTime(override val displayName: String) : Displayable {
    EVERY_DAY("Every Day"),
    A_FEW_TIMES_A_WEEK("A Few Times a Week"),
    SOMETIMES("Sometimes"),
    VERY_RARELY("Very Rarely")
}

enum class AgeGroup(override val displayName: String) : Displayable {
    AGE_5_15("5-15"),
    AGE_16_25("16-25"),
    AGE_26_35("26-35"),
    AGE_35_PLUS("35+")
}

enum class Gender(override val displayName: String) : Displayable {
    FEMALE("Female"),
    MALE("MALE")
}