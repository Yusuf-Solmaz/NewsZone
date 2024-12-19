package com.yms.domain.usecase.news

import androidx.paging.PagingData
import com.yms.domain.model.news.ArticleData
import com.yms.domain.repository.news.NewsRepository
import kotlinx.coroutines.flow.Flow

data class NewsUseCase(
    val getNewsByCategory: GetNewsByCategory,
    val searchNews: SearchNews,
    val getBreakingNews: GetBreakingNews,
    val getPagedNewsByCategory: GetPagedNewsByCategory,
    val searchPagedNews: SearchPagedNews,
    val getNewsByMediator: GetNewsByMediator
)

class GetNewsByMediator(private val newsRepository: NewsRepository) {
    operator fun invoke(category: String?): Flow<PagingData<ArticleData>> {
        return newsRepository.getNewsWithMediator(category)
    }
}

class GetPagedNewsByCategory(private val newsRepository: NewsRepository) {
    operator fun invoke(category: String?): Flow<PagingData<ArticleData>> {
        return newsRepository.getPagedNewsByCategory(category)
    }
}

class SearchPagedNews(private val newsRepository: NewsRepository) {
    operator fun invoke(query: String): Flow<PagingData<ArticleData>> {
        return newsRepository.searchPagedNews(query)
    }
}

