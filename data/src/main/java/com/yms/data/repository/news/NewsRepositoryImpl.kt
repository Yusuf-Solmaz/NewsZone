package com.yms.data.repository.news

import android.util.Log
import com.yms.data.mapper.NewsMapper.toDomain
import com.yms.data.remote.NewsApi
import com.yms.domain.model.news.NewsData
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(val api: NewsApi): NewsRepository {
    override fun getNewsByCategory(
        category: String?,
        page: Int,
        pageSize: Int
    ): Flow<RootResult<NewsData>> = flow {
        emit(RootResult.Loading)

        try {

            val response = api.getNewsByCategory(category = category, page = page, pageSize = pageSize)

            Log.d("NewsRepositoryImpl", "getNewsByCategory: ${response.articleDtos?.size}")

            emit(RootResult.Success(response.toDomain()))

            Log.d("NewsRepositoryImpl", "getNewsByCategory: ${response.toDomain().articleDtos.size}")
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

            emit(RootResult.Success(response.toDomain()))
        }
        catch (e:Exception){
            emit(RootResult.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}