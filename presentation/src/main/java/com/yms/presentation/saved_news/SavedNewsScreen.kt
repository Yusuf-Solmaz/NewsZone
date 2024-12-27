package com.yms.presentation.saved_news

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yms.domain.model.news.BaseArticle
import com.yms.presentation.items.ArticleCard

@Composable
fun SavedNews(viewModel: SavedNewsViewModel = hiltViewModel(),savedArticleToArticleDetail :(BaseArticle) -> Unit) {

    val savedNewsState by viewModel.savedNewsState.collectAsStateWithLifecycle()
    Log.d("SavedNews", "SavedNews: $savedNewsState")
    when (val savedNewsState = savedNewsState) {
        is SavedNewsState.Error -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Error: ${savedNewsState.message}",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        is SavedNewsState.Idle -> {}
        is SavedNewsState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading...",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        is SavedNewsState.Success -> {
            if (savedNewsState.articles.isNullOrEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No saved news",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            else{
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(savedNewsState.articles) {
                        ArticleCard(
                            articleData = it,
                            navigateToArticleDetailScreen = savedArticleToArticleDetail
                        )
                    }
                }
            }

        }

    }
}

