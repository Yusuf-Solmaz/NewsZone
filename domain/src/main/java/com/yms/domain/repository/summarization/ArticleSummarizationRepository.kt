package com.yms.domain.repository.summarization

import com.yms.domain.model.gemini.ArticleSummary
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.flow.Flow

interface ArticleSummarizationRepository {
    fun getSummary(prompt: String,content: String): Flow<RootResult<ArticleSummary>>
}