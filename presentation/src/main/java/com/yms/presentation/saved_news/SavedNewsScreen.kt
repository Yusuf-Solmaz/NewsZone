package com.yms.presentation.saved_news

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yms.domain.model.news.SavedNews
import com.yms.presentation.R

@Composable
fun SavedNews(viewModel: SavedNewsViewModel = hiltViewModel()) {

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
                        SavedNewsCard(savedNews = it, navigateToArticleDetailScreen = {})
                    }
                }
            }

        }

    }
}

@Composable
fun SavedNewsCard(
    savedNews: SavedNews,
    navigateToArticleDetailScreen: (SavedNews) -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            navigateToArticleDetailScreen(savedNews)
        }
    ) {


        Row(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

            SubcomposeAsyncImage(
                model = savedNews.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .padding(end = dimensionResource(R.dimen.padding_small))
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                loading = {
                    LottieAnimation(
                        composition,
                        modifier = Modifier.size(100.dp),
                        iterations = Int.MAX_VALUE
                    )
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = savedNews.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = savedNews.author,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dot),
                        contentDescription = "Dot Icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(5.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = savedNews.timeAgo,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        }

    }
}