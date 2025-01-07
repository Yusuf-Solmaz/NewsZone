package com.yms.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yms.domain.model.news.BaseArticle
import com.yms.presentation.R
import com.yms.presentation.home.content.BreakingNewsSection
import com.yms.presentation.home.content.NewsCategorySection
import com.yms.presentation.home.viewmodel.BreakingNewsState
import com.yms.presentation.home.viewmodel.NewsHomeEvent
import com.yms.presentation.home.viewmodel.NewsHomeViewModel
import com.yms.utils.NewsCategory
import com.yms.utils.ShimmerNewsZoneHome


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsHomeViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit,
    navigateToArticleDetailScreen: (BaseArticle) -> Unit
) {
    val categoryState by viewModel.categoryState.collectAsState()
    val breakingNewsState by viewModel.breakingNewsState.collectAsState()
    val pagedNews = viewModel.pagedNews.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Log.d("isRefreshing", "$isRefreshing")


    val context = LocalContext.current

    val getNewsByCategory: (NewsCategory) -> Unit = { category ->
        viewModel.onEvent(NewsHomeEvent.GetNewsByCategory(category))
    }


    LaunchedEffect(key1 = pagedNews.loadState) {
        if (pagedNews.itemCount > 0) {
            //No Error
        } else if (pagedNews.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                 context.getString(R.string.error) + (pagedNews.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }

    }



    if (!categoryState.isLoading) {
        LaunchedEffect(categoryState.category) {
            getNewsByCategory(categoryState.category)
        }
    }

    ShimmerNewsZoneHome(
        isLoading = (breakingNewsState is BreakingNewsState.Loading || pagedNews.itemCount <= 0)
    ) {
        PullToRefreshBox(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.onEvent(NewsHomeEvent.RefreshPage(categoryState.category))
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        bottom = dimensionResource(R.dimen.padding_medium),
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn {
                    item {
                        BreakingNewsSection(
                            retry = { viewModel.onEvent(NewsHomeEvent.GetBreakingNews) },
                            modifier = Modifier.fillMaxWidth(),
                            breakingNewsState = breakingNewsState,
                            navigateToArticleDetailScreen = navigateToArticleDetailScreen
                        )
                    }
                    item {
                        SearchSection(
                            modifier = Modifier.fillMaxWidth(),
                            navigateToSearchScreen = navigateToSearchScreen
                        )
                    }
                }

                NewsCategorySection(
                    onTabSelected = { category ->
                        categoryState.category = category
                        getNewsByCategory(category)
                    },
                    pagedNews = pagedNews,

                    navigateToArticleDetailScreen = navigateToArticleDetailScreen
                )
            }

        }
    }
}

@Composable
fun SearchSection(modifier: Modifier = Modifier, navigateToSearchScreen: () -> Unit) {
    Row(
        modifier = modifier.padding(vertical = dimensionResource(R.dimen.padding_small)),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.latest_news),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        IconButton(
            onClick = {
                navigateToSearchScreen()
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.search)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsHomeScreenPreview() {
    Row(Modifier.fillMaxSize()) {
        SearchSection(modifier = Modifier.fillMaxWidth(), navigateToSearchScreen = {})

    }
}

