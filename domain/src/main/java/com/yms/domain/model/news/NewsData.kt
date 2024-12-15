package com.yms.domain.model.news

data class NewsData(
    val articleDtos: List<ArticleData>,
)

data class ArticleData(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceDto: SourceData,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class SourceData(
    val id: String,
    val name: String
)
