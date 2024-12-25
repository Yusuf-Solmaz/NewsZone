package com.yms.presentation.home.viewmodel

import com.yms.utils.NewsCategory

sealed interface NewsHomeEvent{
    data class GetNewsByCategory(val category: NewsCategory) : NewsHomeEvent
    data object GetBreakingNews : NewsHomeEvent
}