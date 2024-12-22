package com.yms.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yms.domain.model.news.ArticleData
import com.yms.presentation.R
import com.yms.presentation.home.content.BreakingNewsSection
import com.yms.presentation.home.content.NewsCategorySection
import com.yms.presentation.home.viewmodel.NewsHomeViewModel
import com.yms.utils.NewsCategory


@Composable
fun NewsHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsHomeViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit,
    navigateToArticleDetailScreen: (ArticleData) -> Unit
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

        SearchSection(
            modifier = Modifier.fillMaxWidth(),
            navigateToSearchScreen = navigateToSearchScreen
        )

        NewsCategorySection(
            onTabSelected = { category -> viewModel.getPagedNewsWithMediator(category) },
            pagedNews = pagedNews,
            category = NewsCategory.fromString(categoryState.category.title) ?: NewsCategory.GENERAL,
            navigateToArticleDetailScreen = navigateToArticleDetailScreen
        )
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
            text = "Latest News",
            style = MaterialTheme.typography.titleLarge.copy(
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
            )
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

