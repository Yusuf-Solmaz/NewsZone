package com.yms.presentation.saved_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.news.SavedNews
import com.yms.domain.usecase.saved_news.SavedNewsUseCase
import com.yms.domain.utils.RootResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SavedNewsState(){
    object Idle: SavedNewsState()
    object Loading: SavedNewsState()
    data class Success(val articles: List<SavedNews>?): SavedNewsState()
    data class Error(val message: String): SavedNewsState()
}

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    val savedNewsUseCase: SavedNewsUseCase
) :ViewModel() {
    private val _savedNewsState = MutableStateFlow<SavedNewsState>(SavedNewsState.Idle)
    val savedNewsState: StateFlow<SavedNewsState> = _savedNewsState

    init {
        getAllSavedNews()
    }

    fun onEvent(event: SavedNewsEvent){
        when(event){
            is SavedNewsEvent.GetAllArticles -> getAllSavedNews()
            is SavedNewsEvent.DeleteArticle -> deleteArticle(event.articleUrl)
        }
    }

    private fun deleteArticle(articleUrl: String){
        viewModelScope.launch {
            savedNewsUseCase.deleteSavedNews(articleUrl)
        }
    }

    private fun getAllSavedNews(){
        _savedNewsState.value = SavedNewsState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsUseCase.getSavedNews().collect{
                result->
                when(result){
                    is RootResult.Error -> {
                        _savedNewsState.value = SavedNewsState.Error(result.message)
                    }
                    RootResult.Loading -> {
                        _savedNewsState.value = SavedNewsState.Loading
                    }
                    is RootResult.Success -> {
                        _savedNewsState.value = SavedNewsState.Success(result.data)
                    }
                }

            }
        }
    }
}

