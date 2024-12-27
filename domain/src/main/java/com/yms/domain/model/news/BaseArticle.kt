package com.yms.domain.model.news

interface BaseArticle {
    val id: Int
    val author: String
    val content: String
    val description: String
    val publishedAt: String
    val title: String
    val url: String
    val urlToImage: String
    val timeAgo: String
}