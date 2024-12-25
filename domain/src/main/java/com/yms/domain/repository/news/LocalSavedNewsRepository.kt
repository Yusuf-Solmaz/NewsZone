package com.yms.domain.repository.news

import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.SavedNews
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.flow.Flow

interface LocalSavedNewsRepository {
    suspend fun insertSavedNews(newsData: ArticleData) : RootResult<Boolean>
    fun getAllSavedNews(): Flow<RootResult<List<SavedNews>>>
    suspend fun deleteSavedNews(url: String) : Boolean
    suspend fun isNewsSaved(url: String): Boolean

}