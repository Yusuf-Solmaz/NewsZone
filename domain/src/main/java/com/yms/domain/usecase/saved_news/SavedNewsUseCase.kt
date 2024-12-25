package com.yms.domain.usecase.saved_news

import com.yms.domain.model.news.ArticleData
import com.yms.domain.repository.news.LocalSavedNewsRepository

data class SavedNewsUseCase(
    val getSavedNews: GetSavedNews,
    val deleteSavedNews: DeleteSavedNews,
    val isNewsSaved: IsNewsSaved,
    val insertSavedNews: InsertSavedNews
)

class GetSavedNews(private val localSavedNewsRepository: LocalSavedNewsRepository) {
    operator fun invoke() = localSavedNewsRepository.getAllSavedNews()
}

class DeleteSavedNews(private val localSavedNewsRepository: LocalSavedNewsRepository) {
    suspend operator fun invoke(url: String) = localSavedNewsRepository.deleteSavedNews(url)
}

class IsNewsSaved(private val localSavedNewsRepository: LocalSavedNewsRepository) {
    suspend operator fun invoke(url: String) = localSavedNewsRepository.isNewsSaved(url)
}

class InsertSavedNews(private val localSavedNewsRepository: LocalSavedNewsRepository) {
    suspend operator fun invoke(savedNews: ArticleData) = localSavedNewsRepository.insertSavedNews(savedNews)
}