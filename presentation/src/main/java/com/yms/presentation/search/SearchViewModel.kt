package com.yms.presentation.search

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yms.domain.model.news.ArticleData
import com.yms.domain.usecase.news.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val newsUseCase: NewsUseCase) : ViewModel() {

    private val _searchOptions = MutableStateFlow(SearchOptions())
    val searchOptions: StateFlow<SearchOptions> = _searchOptions

    val pagedSearchNews = MutableStateFlow<PagingData<ArticleData>>(PagingData.empty())


    fun updateSearchOptions(update: SearchOptions.() -> SearchOptions) {
        _searchOptions.value = _searchOptions.value.update()
    }

    fun search() {
        val options = _searchOptions.value
        val transformedSearchIn = options.searchIn.joinToString(",").lowercase()
        val transformedSortBy = options.sortBy?.replaceFirstChar { it.lowercaseChar() }

        searchNews(
            query = options.query,
            sortBy = transformedSortBy,
            searchIn = transformedSearchIn,
            fromDate = options.fromDate.toString(),
            toDate = options.toDate.toString()
        )
    }

    private fun searchNews(
        query: String,
        sortBy: String?,
        searchIn: String,
        fromDate: String?,
        toDate: String?
    ) {
        viewModelScope.launch {
            pagedSearchNews.value = PagingData.empty()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                newsUseCase.searchNews(query = query, sortBy = sortBy,searchIn = searchIn, fromDate = fromDate, toDate = toDate)
                    .cachedIn(viewModelScope)
                    .collect {
                        pagedSearchNews.value = it
                    }
            }else{
                newsUseCase.searchNews(query = query, sortBy = sortBy,searchIn = searchIn, fromDate = null, toDate = null)
                    .cachedIn(viewModelScope)
                    .collect {
                        pagedSearchNews.value = it
                    }
                Log.d("SearchViewModelElse", "searchNews: $query $sortBy $searchIn $fromDate $toDate")
            }
        }
    }


}

data class SearchOptions(
    val query: String = "",
    val sortBy: String? = null,
    val searchIn: List<String> = emptyList(),
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null
)


