package com.yms.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.news.ArticleData
import com.yms.domain.usecase.news.NewsUseCase
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import com.yms.domain.utils.RootResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsHomeViewModel @Inject constructor(
    val customizationPreferencesUseCase: CustomizationPreferencesUseCase,
    val newsUseCase: NewsUseCase
) :
    ViewModel() {

    private companion object {
        const val STOP_TIME_MILLIS = 5_000L
        const val TAG = "NewsHomeViewModel"
    }

    private val _newsState = MutableStateFlow(NewsState())
    val newsState: StateFlow<NewsState>
        get() = _newsState

    private val _breakingNewsState = MutableStateFlow(BreakingNewsState())
    val breakingNewsState: StateFlow<BreakingNewsState>
        get() = _breakingNewsState

    val categoryState: StateFlow<CategoryState> =
        customizationPreferencesUseCase.readCategory().map { category ->
            CategoryState(category = category, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = CategoryState()
        )

    init {
        //getNewsByCategory(category = categoryState.value.category, page = 1, pageSize = 20)
        getBreakingNews()

        Log.d("CategoryState", "$categoryState")
    }

    fun getBreakingNews(page: Int = 1, pageSize: Int = 5) {
        viewModelScope.launch {
            newsUseCase.getBreakingNews(page, pageSize).collect { result ->
                when (result) {
                    is RootResult.Error -> {
                        _breakingNewsState.update { state ->
                            state.copy(isLoading = false, error = result.message)
                        }

                    }

                    RootResult.Loading -> {
                        _breakingNewsState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is RootResult.Success -> {

                        val articles = result.data?.articleDtos ?: emptyList()
                        Log.d("BreakingNews", "Articles: ${articles.get(0)}")
                        _breakingNewsState.update { state ->
                            state.copy(isLoading = false, news = articles)
                        }
                    }
                }
            }
        }
    }

    fun getNewsByCategory(category: String?, page: Int, pageSize: Int) {
        viewModelScope.launch {
            newsUseCase.getNewsByCategory(category, page, pageSize).collect { result ->
                when (result) {
                    is RootResult.Error -> {
                        Log.d(TAG, "Error: ${result.message}")
                        _newsState.update { state ->
                            state.copy(isLoading = false, error = result.message)
                        }
                    }

                    is RootResult.Loading -> {
                        Log.d(TAG, "Loading...")
                        _newsState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is RootResult.Success -> {
                        Log.d(TAG, "Success: ${result.data}")
                        val articles = result.data?.articleDtos ?: emptyList()
                        Log.d(TAG, "Articles: $articles")
                        _newsState.update { state ->
                            state.copy(isLoading = false, news = articles)
                        }
                    }
                }
            }
        }
    }

}


data class NewsState(
    val news: List<ArticleData>? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

data class BreakingNewsState(
    val news: List<ArticleData>? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

data class CategoryState(
    val category: String = "",
    val isLoading: Boolean = true
)