package com.yms.data.repository.local

import com.yms.data.local.dao.SavedNewsDao
import com.yms.data.mapper.NewsMapper.toSavedNews
import com.yms.data.mapper.NewsMapper.toSavedNewsEntity
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.SavedNews
import com.yms.domain.repository.news.LocalSavedNewsRepository
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalSavedNewsRepositoryImpl @Inject constructor(private val savedNewsDao: SavedNewsDao) : LocalSavedNewsRepository {


    override suspend fun insertSavedNews(newsData: ArticleData): RootResult<Boolean> {
        return try {
            savedNewsDao.insertSavedNews(newsData.toSavedNewsEntity())
            RootResult.Success(true)
        } catch (e: Exception) {
            RootResult.Error("Error inserting news: ${e.localizedMessage}")
        }
    }


    override fun getAllSavedNews(): Flow<RootResult<List<SavedNews>>> {
        return flow {
            emit(RootResult.Loading)
            try {

                savedNewsDao.getAllSavedNews()
                    .collect { savedNewsEntities ->

                        val savedNewsList = savedNewsEntities.map { it.toSavedNews() }
                        emit(RootResult.Success(savedNewsList))
                    }
            } catch (e: Exception) {
                emit(RootResult.Error("Error fetching saved news: ${e.localizedMessage}"))
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun deleteSavedNews(url: String): Boolean {
        return try {
            savedNewsDao.deleteSavedNewsByUrl(url)
            true
        } catch (e: Exception) {
            false
        }
    }


    override suspend fun isNewsSaved(url: String): Boolean {
        return savedNewsDao.isNewsSavedByUrl(url) > 0
    }
}
