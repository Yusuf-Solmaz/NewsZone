package com.yms.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yms.domain.model.news.ArticleData
import com.yms.domain.usecase.news.NewsUseCase
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import com.yms.domain.utils.RootResult
import com.yms.utils.NewsCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsHomeViewModel @Inject constructor(
    val customizationPreferencesUseCase: CustomizationPreferencesUseCase,
    val newsUseCase: NewsUseCase,
) : ViewModel() {

    private companion object {
        const val STOP_TIME_MILLIS = 5_000L

    }

    fun onEvent(event: NewsHomeEvent) {
        when (event) {
            is NewsHomeEvent.GetNewsByCategory -> {
                getNewsByCategory(event.category)
            }

            is NewsHomeEvent.GetBreakingNews -> {
                getBreakingNews()
            }

            is NewsHomeEvent.RefreshPage -> {
                onRefresh(event.category)
            }
        }
    }

    val isRefreshing = MutableStateFlow(false)

    private val _breakingNewsState = MutableStateFlow<BreakingNewsState>(BreakingNewsState.Idle)
    val breakingNewsState: StateFlow<BreakingNewsState>
        get() = _breakingNewsState

    val pagedNews = MutableStateFlow<PagingData<ArticleData>>(PagingData.empty())

    val categoryState: StateFlow<CategoryState> =
        customizationPreferencesUseCase.readCategory().map { category ->
            try {
                val enumCategory = NewsCategory.fromString(category) ?: NewsCategory.GENERAL

                CategoryState(category = enumCategory, isLoading = false)
            } catch (e: IllegalArgumentException) {
                CategoryState(category = NewsCategory.GENERAL, isLoading = false, error = e.message)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = CategoryState()
        )

    init {
        getBreakingNews()

        Log.d("CategoryState", "$categoryState")
    }

    private fun onRefresh(category: NewsCategory?) {
        isRefreshing.value = true
        viewModelScope.launch {
            try {
                getBreakingNews()
                getNewsByCategory(category)
                delay(1000)
            } finally {
                isRefreshing.value = false
            }
        }
    }

    private fun getNewsByCategory(category: NewsCategory?) {
        viewModelScope.launch {

            newsUseCase.getNewsByMediator(category?.title)
                .cachedIn(viewModelScope)
                .collect {
                    pagedNews.value = it
                }
        }
    }

    private fun getBreakingNews(page: Int = 1, pageSize: Int = 5) {
        viewModelScope.launch {
            newsUseCase.getBreakingNews(page, pageSize).collect { result ->
                _breakingNewsState.value = when (result) {
                    is RootResult.Error -> {
                        BreakingNewsState.Error(result.message)
                    }

                    is RootResult.Loading -> {
                        BreakingNewsState.Loading
                    }

                    is RootResult.Success -> {

                        BreakingNewsState.Success(result.data?.articleDtos ?: emptyList())
                    }

                    else -> BreakingNewsState.Idle
                }
            }
        }
    }

}

sealed interface BreakingNewsState {
    data class Success(val news: List<ArticleData>) : BreakingNewsState
    data object Loading : BreakingNewsState
    data class Error(val message: String) : BreakingNewsState
    data object Idle : BreakingNewsState
}


data class CategoryState(
    var category: NewsCategory = NewsCategory.GENERAL,
    val isLoading: Boolean = true,
    val error: String? = null
)