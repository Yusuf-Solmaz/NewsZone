package com.yms.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.yms.presentation.R
import com.yms.presentation.home.content.BreakingNewsSection
import com.yms.presentation.home.content.NewsCategorySection
import com.yms.presentation.home.viewmodel.NewsHomeViewModel
import com.yms.utils.NewsCategory


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

        NewsCategorySection(
            onTabSelected = { category -> viewModel.getPagedNewsByCategory(category.title) }, // Pass the title
            pagedNews = pagedNews,
            category = NewsCategory.fromString(categoryState.category.title) ?: NewsCategory.GENERAL

        )
    }
}
