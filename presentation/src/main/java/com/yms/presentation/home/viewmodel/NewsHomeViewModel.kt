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
    val newsUseCase: NewsUseCase,
) :
    ViewModel() {

    private companion object {
        const val STOP_TIME_MILLIS = 5_000L

    }

    val pagedNews = MutableStateFlow<PagingData<ArticleData>>(PagingData.empty())

    fun getPagedNewsWithMediator(category: NewsCategory?) {
        viewModelScope.launch {

            newsUseCase.getNewsByMediator(category?.title) // Burada getNewsByMediator çağırıyoruz
                .cachedIn(viewModelScope)
                .collect {
                    pagedNews.value = it
                }
        }
    }



    private val _breakingNewsState = MutableStateFlow(BreakingNewsState())
    val breakingNewsState: StateFlow<BreakingNewsState>
        get() = _breakingNewsState

    val categoryState: StateFlow<CategoryState> =
        customizationPreferencesUseCase.readCategory().map { category ->
            try {
                val enumCategory = NewsCategory.fromString(category) ?: NewsCategory.GENERAL

                CategoryState(category = enumCategory, isLoading = false)
            } catch (e: IllegalArgumentException) {
                CategoryState(category = NewsCategory.GENERAL, isLoading = false,error = e.message)
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

}


data class BreakingNewsState(
    val news: List<ArticleData>? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

data class CategoryState(
    val category: NewsCategory = NewsCategory.GENERAL,
    val isLoading: Boolean = true,
    val error: String? = null
)