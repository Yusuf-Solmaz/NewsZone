package com.yms.presentation.article_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.news.ArticleData
import com.yms.domain.usecase.saved_news.SavedNewsUseCase
import com.yms.domain.utils.RootResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface InsertNewsState{
    data object Success : InsertNewsState
    data class Error(val message: String) : InsertNewsState
    data object Idle : InsertNewsState
}

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val savedNewsUseCase: SavedNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<InsertNewsState>(InsertNewsState.Idle)
    val state: StateFlow<InsertNewsState>
        get() = _state

    private val _isArticleSaved = MutableStateFlow(false)
    val isArticleSaved: StateFlow<Boolean>
        get() = _isArticleSaved

    fun onEvent(event: ArticleDetailEvent) {
        when (event) {
            is ArticleDetailEvent.InsertArticle -> insertArticle(event.article)

            is ArticleDetailEvent.IsArticleSaved -> checkIfArticleIsSaved(event.articleUrl)

            is ArticleDetailEvent.DeleteArticle -> deleteArticle(event.articleUrl)
        }
    }

    private fun deleteArticle(articleUrl: String) {
        viewModelScope.launch {
            savedNewsUseCase.deleteSavedNews(articleUrl)
            _isArticleSaved.value = false
        }
    }


    private fun checkIfArticleIsSaved(articleUrl: String) {
        viewModelScope.launch {
            val isSaved = savedNewsUseCase.isNewsSaved(articleUrl)
            _isArticleSaved.value = isSaved
        }
    }

    private fun insertArticle(article: ArticleData) {
        viewModelScope.launch {
            val result = savedNewsUseCase.insertSavedNews(article)
            _state.value = when (result) {
                is RootResult.Success -> {

                    _isArticleSaved.value = true
                    InsertNewsState.Success
                }
                is RootResult.Error -> {

                    InsertNewsState.Error(result.message)
                }
                else -> InsertNewsState.Idle
            }
        }
    }
}