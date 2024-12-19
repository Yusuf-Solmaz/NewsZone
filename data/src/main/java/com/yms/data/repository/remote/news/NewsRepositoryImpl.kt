package com.yms.data.repository.remote.news

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.yms.data.local.db.NewsDatabase
import com.yms.data.mapper.NewsMapper.toArticleData
import com.yms.data.mapper.NewsMapper.toNews
import com.yms.data.remote.NewsApi
import com.yms.data.remote.NewsRemoteMediator
import com.yms.data.repository.pagination.NewsPagingSource
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.NewsData
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(val api: NewsApi, val newsDatabase: NewsDatabase): NewsRepository {


    override fun getNewsWithMediator(category: String?): Flow<PagingData<ArticleData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            ),
            remoteMediator = NewsRemoteMediator(
                newsApi = api,
                newsDb = newsDatabase,
                category = category
            ),
            pagingSourceFactory = { newsDatabase.dao.pagingSource() }
        ).flow
            .map { pagingData ->
                pagingData
                    .filter { newsEntity ->
                        newsEntity.urlToImage.isNotBlank()
                    }
                    .map { newsEntity -> newsEntity.toArticleData() }
            }
    }

    override fun getPagedNewsByCategory(category: String?): Flow<PagingData<ArticleData>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false, initialLoadSize = 5),
            pagingSourceFactory = { NewsPagingSource(api, category, query = null, source = null) }
        ).flow
    }

    override fun searchPagedNews(query: String): Flow<PagingData<ArticleData>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            pagingSourceFactory = { NewsPagingSource(api, null, query = query, source = null) }
        ).flow
    }

    override fun getNewsByCategory(
        category: String?,
        page: Int,
        pageSize: Int,
        source: String?,
    ): Flow<RootResult<NewsData>> = flow {
        emit(RootResult.Loading)

        try {

            val response = api.getNewsByCategory(category = category, page = page, pageSize = pageSize, source = source)

            emit(RootResult.Success(response.toNews()))

        }
        catch (e:Exception){
            emit(RootResult.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    override fun searchNews(
        query: String,
        sortBy: String?,
        page: Int,
        pageSize: Int
    ): Flow<RootResult<NewsData>> = flow {
        emit(RootResult.Loading)
        try {
            val response = api.searchNews(query = query, sortBy = sortBy, page = page, pageSize = pageSize)

            emit(RootResult.Success(response.toNews()))
        }
        catch (e:Exception){
            emit(RootResult.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}