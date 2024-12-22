package com.yms.utils

enum class NewsCategory(val title: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    companion object {
        // String'i NewsCategory'ye dönüştürmek için yardımcı fonksiyon
        fun fromString(value: String): NewsCategory? {
            return NewsCategory.entries.find { it.title.equals(value, ignoreCase = true) }
        }
    }
}

val newsTabTitles = NewsCategory.entries.map { it.title }
