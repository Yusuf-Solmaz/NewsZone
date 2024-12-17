package com.yms.data.mapper

import com.yms.data.remote.dto.news.ArticleDto
import com.yms.data.remote.dto.news.NewsRoot
import com.yms.data.remote.dto.news.SourceDto
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.NewsData
import com.yms.domain.model.news.SourceData
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object NewsMapper {

    fun NewsRoot.toNews(): NewsData {
        return NewsData(
            articleDtos = articleDtos?.map { it.toArticle() } ?: emptyList()
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
            urlToImage = urlToImage ?: "Data not available",
            timeAgo = timeAgo
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
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", // Milisaniyeli format
                "yyyy-MM-dd'T'HH:mm:ss'Z'"     // Milisaniyesiz format
            )

            val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault()

            var date: java.util.Date? = null // Tipini java.util.Date? olarak belirt
            for (format in formats) {
                try {
                    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
                    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                    date = inputFormat.parse(it)
                    break // Başarıyla parse edilirse döngüden çık
                } catch (e: Exception) {
                    // Hata alırsak diğer formata geç
                }
            }
            date?.let { outputFormat.format(it) } ?: "Data not available"
        } ?: "Data not available"
    }

    private fun calculateTimeAgo(publishedAt: String?): String {
        return publishedAt?.let {
            val formats = listOf(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", // Milisaniyeli format
                "yyyy-MM-dd'T'HH:mm:ss'Z'"     // Milisaniyesiz format
            )

            var publishedDate: java.util.Date? = null
            for (format in formats) {
                try {
                    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
                    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                    publishedDate = inputFormat.parse(it)
                    break // Başarıyla parse edilirse döngüden çık
                } catch (e: Exception) {
                    // Hata alırsak diğer formata geç
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
}
