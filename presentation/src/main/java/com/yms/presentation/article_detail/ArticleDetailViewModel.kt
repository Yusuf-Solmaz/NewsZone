package com.yms.presentation.article_detail

import android.util.Log
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

data class NewsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

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

    fun onEvent(event: ArticleDetailEvent) {
        when (event) {
            is ArticleDetailEvent.InsertArticle -> {
                insertArticle(event.article)
            }
        }
    }

    private fun insertArticle(article: ArticleData) {
        viewModelScope.launch {
            val result = savedNewsUseCase.insertSavedNews(article)
            _state.value = when (result) {
                is RootResult.Success -> {
                    Log.d("ArticleDetailViewModel", "Article saved successfully")
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