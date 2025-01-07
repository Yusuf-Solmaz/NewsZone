package com.yms.presentation.search

sealed interface SearchScreenEvent{
    object Search : SearchScreenEvent
    data class UpdateSearchOptions(val update: SearchOptions.() -> SearchOptions) : SearchScreenEvent

}