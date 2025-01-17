package com.yms.data.mapper

import com.yms.data.local.model.CachedNewsEntity
import com.yms.data.local.model.SavedNewsEntity
import com.yms.data.remote.dto.gemini.ArticleSummaryDto
import com.yms.data.remote.dto.news.ArticleDto
import com.yms.data.remote.dto.news.NewsRoot
import com.yms.data.remote.dto.news.SourceDto
import com.yms.domain.model.gemini.ArticleSummary
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.NewsData
import com.yms.domain.model.news.SavedNews
import com.yms.domain.model.news.SourceData
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object NewsMapper {

    fun SavedNewsEntity.toSavedNews(): SavedNews {
        return SavedNews(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
            timeAgo = timeAgo,
            sourceName = sourceName,
            id = id
        )
    }

    fun ArticleData.toSavedNewsEntity(): SavedNewsEntity {
        return SavedNewsEntity(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
            timeAgo = timeAgo,
            sourceName = sourceDto.name
        )
    }

    fun ArticleData.toNewsEntity(): CachedNewsEntity {
        return CachedNewsEntity(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage,
            timeAgo = timeAgo,
            sourceName = sourceDto.name
        )
    }

    fun NewsRoot.toNews(): NewsData {
        return NewsData(
            articleDtos = articleDtos?.map { it.toArticle() } ?: emptyList()
        )
    }


    fun CachedNewsEntity.toArticleData(): ArticleData {
        return ArticleData(
            id = id,
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            sourceDto = SourceData(
                id = "",
                name = sourceName
            ),
            title = title,
            url = url,
            urlToImage = urlToImage,
            timeAgo = timeAgo
        )
    }

    fun ArticleDto.toArticle(): ArticleData {
        val formattedPublishedAt = formatPublishedAt(publishedAt)
        val timeAgo = calculateTimeAgo(publishedAt)

        return ArticleData(
            author = author ?: "Data not available",
            content = content ?: "Data not available",
            description = description ?: "Data not available",
            publishedAt = formattedPublishedAt,
            sourceDto = sourceDto?.toSource() ?: SourceData(
                "Data not available",
                "Data not available"
            ),
            title = title ?: "Data not available",
            url = url ?: "Data not available",
            urlToImage = urlToImage ?: "",
            timeAgo = timeAgo,
            id = 0
        )
    }

    fun SourceDto.toSource(): SourceData {
        return SourceData(
            id = id ?: "Data not available",
            name = name ?: "Data not available"
        )
    }

    private fun formatPublishedAt(publishedAt: String?): String {
        return publishedAt?.let {
            val formats = listOf(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
            )

            val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault()

            var date: java.util.Date? = null
            for (format in formats) {
                try {
                    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
                    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                    date = inputFormat.parse(it)
                    break
                } catch (e: Exception) {

                }
            }
            date?.let { outputFormat.format(it) } ?: "Data not available"
        } ?: "Data not available"
    }

    private fun calculateTimeAgo(publishedAt: String?): String {
        return publishedAt?.let {
            val formats = listOf(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
            )

            var publishedDate: java.util.Date? = null
            for (format in formats) {
                try {
                    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
                    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                    publishedDate = inputFormat.parse(it)
                    break
                } catch (e: Exception) {

                }
            }

            publishedDate?.let { published ->
                val diff = System.currentTimeMillis() - published.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24

                when {
                    days > 0 -> "${days}d ago"
                    hours > 0 -> "${hours}h ago"
                    minutes > 0 -> "${minutes}m ago"
                    else -> "${seconds}s ago"
                }
            } ?: "Data not available"
        } ?: "Data not available"
    }

    fun ArticleSummaryDto.toArticleSummary(): ArticleSummary {
        return ArticleSummary(
            summary = summary
        )
    }
}
