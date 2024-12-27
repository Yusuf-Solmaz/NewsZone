package com.yms.domain.model.news

data class SavedNews(
    override val id: Int,
    override val author: String,
    override val content: String,
    override val description: String,
    override val publishedAt: String,
    val sourceName: String,
    override val title: String,
    override val url: String,
    override val urlToImage: String,
    override val timeAgo: String
) : BaseArticle
