package com.yms.presentation.article_detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.BaseArticle
import com.yms.domain.usecase.saved_news.SavedNewsUseCase
import com.yms.domain.utils.RootResult
import com.yms.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface InsertNewsState{
    data object Success : InsertNewsState
    data class Error(val message: String) : InsertNewsState
    data object Idle : InsertNewsState
}

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedNewsUseCase: SavedNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<InsertNewsState>(InsertNewsState.Idle)
    val state: StateFlow<InsertNewsState>
        get() = _state

    private val _isArticleSaved = MutableStateFlow(false)
    val isArticleSaved: StateFlow<Boolean>
        get() = _isArticleSaved

    fun onEvent(event: ArticleDetailEvent) {
        when (event) {
            is ArticleDetailEvent.ShareArticle -> shareArticle(event.articleTitle, event.articleUrl)

            is ArticleDetailEvent.GoToUrl -> goToUrl(event.url)

            is ArticleDetailEvent.InsertArticle -> insertArticle(event.article)

            is ArticleDetailEvent.IsArticleSaved -> checkIfArticleIsSaved(event.articleUrl)

            is ArticleDetailEvent.DeleteArticle -> deleteArticle(event.articleUrl)
        }
    }

    private fun shareArticle(articleTitle: String, articleUrl: String?) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                context.getString(R.string.share_text, articleTitle, articleUrl)
            )
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val shareIntent = Intent.createChooser(sendIntent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                context.getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun goToUrl(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    private fun deleteArticle(articleUrl: String) {
        viewModelScope.launch {
            savedNewsUseCase.deleteSavedNews(articleUrl)
            _isArticleSaved.value = false
        }
    }


    private fun checkIfArticleIsSaved(articleUrl: String) {
        viewModelScope.launch {
            val isSaved = savedNewsUseCase.isNewsSaved(articleUrl)
            _isArticleSaved.value = isSaved
        }
    }

    private fun insertArticle(article: BaseArticle) {
        viewModelScope.launch {
            val result = savedNewsUseCase.insertSavedNews(article as ArticleData)
            _state.value = when (result) {
                is RootResult.Success -> {

                    _isArticleSaved.value = true
                    InsertNewsState.Success
                }
                is RootResult.Error -> {

                    InsertNewsState.Error(result.message)
                }
                else -> InsertNewsState.Idle
            }
        }
    }
}