package com.yms.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _searchOptions = MutableStateFlow(SearchOptions())
    val searchOptions: StateFlow<SearchOptions> = _searchOptions

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
            fromDate = options.fromDate,
            toDate = options.toDate
        )
    }

    private fun searchNews(
        query: String?,
        sortBy: String?,
        searchIn: String,
        fromDate: LocalDate?,
        toDate: LocalDate?
    ) {
        Log.d(
            "SearchViewModel",
            "Searching with query: $query sortBy: $sortBy searchIn: $searchIn fromDate: $fromDate toDate: $toDate"
        )
    }
}

data class SearchOptions(
    val query: String = "",
    val sortBy: String? = null,
    val searchIn: List<String> = emptyList(),
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null
)


