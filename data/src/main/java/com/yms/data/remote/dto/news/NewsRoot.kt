package com.yms.data.remote.dto.news

import com.google.gson.annotations.SerializedName

data class NewsRoot(
    @SerializedName("articles")
    val articleDtos: List<ArticleDto>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?
)