package com.yms.presentation.saved_news

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yms.domain.model.news.BaseArticle
import com.yms.presentation.R
import com.yms.presentation.items.ArticleCard
import com.yms.utils.LoadingLottie
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
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
                    text = "${stringResource(R.string.error)} ${savedNewsState.message}",
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
                LoadingLottie()
            }
        }

        is SavedNewsState.Success -> {
            if (savedNewsState.articles.isNullOrEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.no_saved_news),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            else{
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = savedNewsState.articles,
                        key = { article -> article.url }
                    ) {
                        article ->

                        SwipeToDeleteContainer(
                            item = article,
                            onDelete =
                            { article ->
                                viewModel.onEvent(SavedNewsEvent.DeleteArticle(article.url))
                            }
                        ) { article ->
                                ArticleCard(
                                    articleData = article,
                                    navigateToArticleDetailScreen = savedArticleToArticleDetail
                                )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }

    val state = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {

        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground()
            },
            content = { content(item) }
        )
    }
}

@Composable
fun DeleteBackground(
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

