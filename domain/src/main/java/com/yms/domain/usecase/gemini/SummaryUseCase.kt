package com.yms.domain.usecase.gemini

import com.yms.domain.repository.summarization.ArticleSummarizationRepository

data class SummaryUseCase(
    val getSummary: GetSummary
)

class GetSummary(val repository: ArticleSummarizationRepository) {
    operator fun invoke(prompt: String,content: String) = repository.getSummary(prompt,content)
}
