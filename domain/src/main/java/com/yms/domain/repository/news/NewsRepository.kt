package com.yms.domain.repository.news

import androidx.paging.PagingData
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.NewsData
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getPagedNewsByCategory(category: String?): Flow<PagingData<ArticleData>>
    fun searchPagedNews(query: String): Flow<PagingData<ArticleData>>

    fun getNewsByCategory(category: String?, page: Int, pageSize: Int, source: String?): Flow<RootResult<NewsData>>
    fun searchNews(query: String, sortBy: String?, page: Int, pageSize: Int): Flow<RootResult<NewsData>>

    fun getNewsWithMediator(category: String?): Flow<PagingData<ArticleData>>

}