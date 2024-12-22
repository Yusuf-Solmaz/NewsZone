package com.yms.domain.usecase.news

import androidx.paging.PagingData
import com.yms.domain.model.news.ArticleData
import com.yms.domain.repository.news.NewsRepository
import kotlinx.coroutines.flow.Flow

data class NewsUseCase(
    val searchNews: SearchNews,
    val getBreakingNews: GetBreakingNews,
    val getNewsByMediator: GetNewsByMediator
)

class SearchNews(val newsRepository: NewsRepository){
    operator fun invoke(query: String, sortBy: String?, searchIn: String, fromDate: String?, toDate: String?): Flow<PagingData<ArticleData>> = newsRepository.searchPagedNews(query, sortBy, searchIn, fromDate, toDate)
}


class GetNewsByMediator(private val newsRepository: NewsRepository) {
    operator fun invoke(category: String?): Flow<PagingData<ArticleData>> {
        return newsRepository.getNewsWithMediator(category)
    }
}

class GetBreakingNews (val newsRepository: NewsRepository){
    operator fun invoke(page: Int, pageSize: Int) = newsRepository.getBreakingNews(category = null ,page = 1, pageSize, source = "cnn")
}


