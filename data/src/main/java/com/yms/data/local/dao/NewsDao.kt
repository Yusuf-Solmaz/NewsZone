package com.yms.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yms.data.local.model.NewsEntity

@Dao
interface NewsDao {

    @Upsert
    suspend fun upsertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun pagingSource(): PagingSource<Int, NewsEntity>

    @Query("DELETE FROM news")
    suspend fun clearAll()
}