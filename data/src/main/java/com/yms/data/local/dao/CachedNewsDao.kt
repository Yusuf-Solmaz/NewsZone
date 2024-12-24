package com.yms.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yms.data.local.model.CachedNewsEntity

@Dao
interface CachedNewsDao {

    @Upsert
    suspend fun upsertAll(news: List<CachedNewsEntity>)

    @Query("SELECT * FROM cached_news")
    fun pagingSource(): PagingSource<Int, CachedNewsEntity>

    @Query("DELETE FROM cached_news")
    suspend fun clearAll()
}