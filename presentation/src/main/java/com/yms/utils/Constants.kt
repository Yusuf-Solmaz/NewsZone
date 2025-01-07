package com.yms.utils

import com.yms.presentation.R

enum class NewsCategory(val title: String, val displayName: Int){
    GENERAL("general", R.string.general),
    BUSINESS("business", R.string.business),
    SCIENCE("science", R.string.science),
    SPORTS("sports", R.string.sports),
    TECHNOLOGY("technology", R.string.technology),
    ENTERTAINMENT("entertainment", R.string.entertainment),
    HEALTH("health", R.string.health);

    companion object {
        fun fromString(value: String): NewsCategory? {
            return NewsCategory.entries.find { it.title.equals(value, ignoreCase = true) }
        }
    }
}
