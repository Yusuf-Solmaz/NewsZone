package com.yms.domain.model.news

data class SavedNews(
    val id: Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val timeAgo: String
)
