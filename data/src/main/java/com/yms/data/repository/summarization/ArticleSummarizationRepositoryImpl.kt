package com.yms.data.repository.summarization


import android.content.Context
import com.google.ai.client.generativeai.type.content
import com.yms.data.R
import com.yms.data.mapper.NewsMapper.toArticleSummary
import com.yms.data.remote.dto.gemini.ArticleSummaryDto
import com.yms.data.utils.generativeModel
import com.yms.domain.model.gemini.ArticleSummary
import com.yms.domain.repository.summarization.ArticleSummarizationRepository
import com.yms.domain.utils.RootResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArticleSummarizationRepositoryImpl(val context: Context) : ArticleSummarizationRepository {

    override fun getSummary(prompt: String,content: String): Flow<RootResult<ArticleSummary>> = flow {
        emit(RootResult.Loading)

        val response = generativeModel.generateContent(
            content {
                text("$prompt $content")
            }
        ).text ?: context.getString(R.string.error_summary)

        val articleSummary = ArticleSummaryDto(response)

        emit(RootResult.Success(articleSummary.toArticleSummary()))

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(RootResult.Error(it.message ?: "Something went wrong"))
        }
}