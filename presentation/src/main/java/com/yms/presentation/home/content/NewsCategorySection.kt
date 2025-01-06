package com.yms.presentation.home.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.yms.domain.model.news.ArticleData
import com.yms.domain.model.news.BaseArticle
import com.yms.presentation.items.ArticleCard
import com.yms.utils.NewsCategory
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCategorySection(
    onTabSelected: (NewsCategory) -> Unit, // Update to use enum
    modifier: Modifier = Modifier,
    pagedNews: LazyPagingItems<ArticleData>,
    navigateToArticleDetailScreen: (BaseArticle) -> Unit
) {
    val selectedTabIndex = remember { mutableStateOf(0) }
    val animatedVisibilityStates = remember { mutableStateOf<List<Boolean>>(emptyList()) }

    LaunchedEffect(pagedNews.itemSnapshotList) {

        animatedVisibilityStates.value = List(pagedNews.itemCount) { false }
        pagedNews.itemSnapshotList.forEachIndexed { index, _ ->
            delay(100L * index)
            animatedVisibilityStates.value = animatedVisibilityStates.value.toMutableList().apply {
                this[index] = true
            }
        }
    }

    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = modifier.fillMaxWidth(),
            edgePadding = 8.dp
        ) {
            NewsCategory.entries.forEachIndexed { index, categoryEnum ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        selectedTabIndex.value = index
                        onTabSelected(categoryEnum)
                    },
                    text = {
                        Text(
                            text = categoryEnum.title.replaceFirstChar { it.uppercase() },
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

                        ArticleCard(
                            articleData = article,
                            navigateToArticleDetailScreen = navigateToArticleDetailScreen
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

