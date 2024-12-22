package com.yms.data.remote


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yms.data.local.db.NewsDatabase
import com.yms.data.local.model.NewsEntity
import com.yms.data.mapper.NewsMapper.articleDataToNewsEntity
import com.yms.data.mapper.NewsMapper.toNews
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApi: NewsApi,
    private val newsDb: NewsDatabase,
    private val category: String?,
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                REFRESH -> 1
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val response = newsApi.getNewsByCategory(
                category = category,
                page = loadKey,
                pageSize = state.config.pageSize,
                source = null
            )

            val newsData = response.toNews()
            val newsEntities: List<NewsEntity> = newsData.articleDtos.map { articleData ->
                articleDataToNewsEntity(articleData)
            }

            newsDb.withTransaction {
                if (loadType == REFRESH) {
                    newsDb.dao.clearAll()
                }
                newsDb.dao.upsertAll(newsEntities)
                Log.d("NewsRemoteMediator", "Inserted ${newsEntities.size} news entities")
            }

            MediatorResult.Success(endOfPaginationReached = response.articleDtos.isNullOrEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}