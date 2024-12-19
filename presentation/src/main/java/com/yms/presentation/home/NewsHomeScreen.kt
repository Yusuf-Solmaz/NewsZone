package com.yms.presentation.home

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yms.domain.model.news.ArticleData
import com.yms.presentation.R
import com.yms.presentation.home.content.BreakingNewsSection
import com.yms.presentation.home.viewmodel.NewsHomeViewModel


@Composable
fun NewsHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsHomeViewModel = hiltViewModel()
) {
    val categoryState by viewModel.categoryState.collectAsState()
    val pagedNews = viewModel.pagedNews.collectAsLazyPagingItems()

    val context = LocalContext.current

    LaunchedEffect(key1 = pagedNews.loadState) {
        if (pagedNews.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (pagedNews.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    if (!categoryState.isLoading) {
        LaunchedEffect(categoryState.category) {
            viewModel.getPagedNewsWithMediator(categoryState.category)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
            .background(MaterialTheme.colorScheme.background)
    ) {
        BreakingNewsSection(breakingNewsState = viewModel.breakingNewsState.collectAsState().value)

        ScrollableTabRowExample(onTabSelected = { category ->
            viewModel.getPagedNewsByCategory(category)
        }, pagedNews = pagedNews, category = categoryState.category)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableTabRowExample(
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    pagedNews: LazyPagingItems<ArticleData>,
    category: String
) {
    val tabTitles = listOf(
        "business", "entertainment", "general", "health", "science", "sports", "technology"
    )

    val selectedTabIndex = remember { mutableStateOf(0) }

    LaunchedEffect(category) {
        val initialIndex = tabTitles.indexOf(category).coerceAtLeast(0)
        selectedTabIndex.value = initialIndex
    }

    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = modifier.fillMaxWidth(),
            edgePadding = 8.dp
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        selectedTabIndex.value = index
                        onTabSelected(title)
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(pagedNews.itemCount) { index ->
                val article = pagedNews[index]

                if (article != null) {
                    Log.d("NewsHomeScreen", "Article: ${article.urlToImage}")
                    NewsCard(
                        imageUrl = article.urlToImage,
                        title = article.title,
                        author = article.author,
                        date = article.timeAgo
                    )
                }
            }
            item {
                if (pagedNews.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    imageUrl: String,
    title: String,
    author: String,
    date: String
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background),
        shape = MaterialTheme.shapes.medium
    ) {


            Row(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

                SubcomposeAsyncImage(
                    model = imageUrl,
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
                        text = title,
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
                            text = author,
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
                            text = date,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

            }

        }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        ) {
        NewsCard(
            imageUrl = "https://static.foxnews.com/foxnews.com/content/uploads/2024/12/james-lankford.jpg",
            title = "Sample News Title",
            author = "Author Name",
            date = "2023-09-01"
        )

    }

}
