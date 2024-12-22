package com.yms.domain.repository.news

import androidx.paging.PagingData
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.NewsData
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun searchPagedNews(query: String, sortBy: String?, searchIn: String, fromDate: String?, toDate: String?): Flow<PagingData<ArticleData>>
    fun getNewsWithMediator(category: String?): Flow<PagingData<ArticleData>>
    fun getBreakingNews(category: String?, page: Int, pageSize: Int, source: String?): Flow<RootResult<NewsData>>

}