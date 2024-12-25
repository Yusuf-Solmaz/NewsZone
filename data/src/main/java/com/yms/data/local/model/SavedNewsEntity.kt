package com.yms.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news")
data class SavedNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val timeAgo: String = "",
    val sourceName: String = ""
)