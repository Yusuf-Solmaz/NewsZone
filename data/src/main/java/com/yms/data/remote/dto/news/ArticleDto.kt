package com.yms.data.remote.dto.news

data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val sourceDto: SourceDto?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)