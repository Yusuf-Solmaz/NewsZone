package com.yms.data.mapper

import com.yms.data.remote.dto.news.ArticleDto
import com.yms.data.remote.dto.news.NewsRoot
import com.yms.data.remote.dto.news.SourceDto
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.NewsData
import com.yms.domain.model.news.SourceData

object NewsMapper {

    fun NewsRoot.toDomain(): NewsData {
        return NewsData(
            articleDtos = articleDtos?.map { it.toDomain() } ?: emptyList() // Null ise boş liste döndür
        )
    }

    fun ArticleDto.toDomain(): ArticleData {
        return ArticleData(
            author = author ?: "", // Null ise boş string döndür
            content = content ?: "",
            description = description ?: "",
            publishedAt = publishedAt ?: "",
            sourceDto = sourceDto?.toDomain() ?: SourceData("", ""), // Null ise boş SourceData döndür
            title = title ?: "",
            url = url ?: "",
            urlToImage = urlToImage ?: ""
        )
    }

    fun SourceDto.toDomain(): SourceData {
        return SourceData(
            id = id ?: "", // Null ise boş string döndür
            name = name ?: ""
        )
    }
}
