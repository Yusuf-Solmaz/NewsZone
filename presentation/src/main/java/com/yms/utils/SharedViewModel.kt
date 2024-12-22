package com.yms.utils

import androidx.lifecycle.ViewModel
import com.yms.domain.model.news.ArticleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
): ViewModel() {

    private val _sharedState = MutableStateFlow<ArticleState>(ArticleState())
    val sharedState: StateFlow<ArticleState>
        get() = _sharedState

    fun updateState(article: ArticleData? = null){
        try {
            _sharedState.value = _sharedState.value.copy(article = article,isLoading = false,error = null)
        }
        catch (e:Exception){
            _sharedState.value = _sharedState.value.copy(isLoading = false,error = e.message)
        }

    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}

data class ArticleState(
    val article: ArticleData? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)