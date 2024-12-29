package com.yms.presentation.saved_news

sealed interface SavedNewsEvent {
    data object GetAllArticles : SavedNewsEvent
    data class DeleteArticle(val articleUrl: String) : SavedNewsEvent

}