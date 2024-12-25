package com.yms.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yms.data.local.model.SavedNewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedNews(savedNewsEntity: SavedNewsEntity)

    @Query("SELECT * FROM saved_news")
    fun getAllSavedNews(): Flow<List<SavedNewsEntity>>

    @Query("DELETE FROM saved_news WHERE url = :url")
    suspend fun deleteSavedNewsByUrl(url: String)

    @Query("SELECT COUNT(*) FROM saved_news WHERE url = :url")
    suspend fun isNewsSavedByUrl(url: String): Int


}