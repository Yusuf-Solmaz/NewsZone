package com.yms.utils

val newsTabTitles = listOf(
    "business", "entertainment", "general", "health", "science", "sports", "technology"
)

enum class NewsTabTitles(val title: String){
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}