package com.yms.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.news.BaseArticle
import com.yms.domain.usecase.gemini.SummaryUseCase
import com.yms.domain.utils.RootResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(val summaryUseCase: SummaryUseCase) : ViewModel() {

    private val _sharedArticleState = MutableStateFlow<SharedArticleState>(SharedArticleState())
    val sharedArticleState: StateFlow<SharedArticleState>
        get() = _sharedArticleState

    private val _summaryState = MutableStateFlow<SummaryState>(SummaryState.Idle)
    val summaryState: StateFlow<SummaryState>
        get() = _summaryState

    fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.GetSummary -> {
                getSummary(event.prompt,event.content)
            }
        }
    }

    private fun getSummary(prompt: String, content: String) {
        _summaryState.value = SummaryState.Loading

        viewModelScope.launch {
            summaryUseCase.getSummary(prompt = prompt,content).collect {
                result ->
                when (result) {
                    is RootResult.Success -> {
                        _summaryState.value = SummaryState.Success(result.data?.summary ?: "Something went wrong")
                    }
                    is RootResult.Error -> {
                        _summaryState.value = SummaryState.Error(result.message)
                    }
                    is RootResult.Loading -> {
                        _summaryState.value = SummaryState.Loading
                    }
                }
            }
        }
    }

    fun updateState(article: BaseArticle? = null) {
        try {
            _sharedArticleState.value = _sharedArticleState.value.copy(
                article = article,
                isLoading = false,
                error = null
            )
        } catch (e: Exception) {
            _sharedArticleState.value =
                _sharedArticleState.value.copy(isLoading = false, error = e.message)
        }

    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}


sealed interface SummaryEvent {
    data class GetSummary(val prompt: String,val content: String) : SummaryEvent
}

sealed interface SummaryState {
    object Idle : SummaryState
    object Loading : SummaryState
    data class Success(val summary: String) : SummaryState
    data class Error(val message: String) : SummaryState
}

data class SharedArticleState(
    val article: BaseArticle? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)