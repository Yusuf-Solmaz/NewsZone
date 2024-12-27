package com.yms.domain.model.news

data class NewsData(
    val articleDtos: List<ArticleData>,
)

data class ArticleData(
    override val id: Int,
    override val author: String,
    override val content: String,
    override val description: String,
    override val publishedAt: String,
    val sourceDto: SourceData,
    override val title: String,
    override val url: String,
    override val urlToImage: String,
    override val timeAgo: String
) : BaseArticle

data class SourceData(
    val id: String,
    val name: String
)
