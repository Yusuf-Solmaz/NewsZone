package com.yms.presentation.article_detail

import com.yms.domain.model.news.BaseArticle

sealed interface ArticleDetailEvent {
    data class InsertArticle(val article: BaseArticle) : ArticleDetailEvent
    data class IsArticleSaved(val articleUrl: String) : ArticleDetailEvent
    data class DeleteArticle(val articleUrl: String) : ArticleDetailEvent
}