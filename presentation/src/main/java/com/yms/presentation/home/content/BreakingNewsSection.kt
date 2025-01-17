package com.yms.presentation.home.content

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yms.domain.model.news.ArticleData
import com.yms.presentation.R
import com.yms.presentation.home.viewmodel.BreakingNewsState
import kotlinx.coroutines.delay

@Composable
fun BreakingNewsSection(
    modifier: Modifier = Modifier,
    retry: () -> Unit,
    breakingNewsState: BreakingNewsState,
    navigateToArticleDetailScreen: (ArticleData) -> Unit
) {
    Column(modifier = modifier.wrapContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.breaking_news),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        when (val state = breakingNewsState) {
            is BreakingNewsState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    Button(onClick = retry) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }

            BreakingNewsState.Loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.loading))
                }
            }

            is BreakingNewsState.Success -> {
                BreakingNewsCard(articleList = state.news,navigateToArticleDetailScreen = navigateToArticleDetailScreen)
            }

            else -> Unit
        }
    }
}

@Composable
fun BreakingNewsCard(modifier: Modifier = Modifier, articleList: List<ArticleData>,navigateToArticleDetailScreen: (ArticleData) -> Unit) {

    val pagerState = rememberPagerState(
        pageCount = { articleList.size }
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(15000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        var isImageLoading by remember { mutableStateOf(true) }
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))


        Box(modifier = modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                modifier.wrapContentSize()
            ) { currentPage ->
                val article = articleList[currentPage]

                Card(
                    onClick = {
                        navigateToArticleDetailScreen(article)
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small),
                        ),
                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_big))
                ) {
                    Box {
                        SubcomposeAsyncImage(
                            model = article.urlToImage,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp),
                            contentScale = ContentScale.Crop,
                            loading = {
                                LottieAnimation(
                                    composition,
                                    modifier = Modifier.size(100.dp),
                                    iterations = Int.MAX_VALUE
                                )
                            },
                            onSuccess = {
                                isImageLoading = false
                            },
                            onError = {
                                isImageLoading = false
                            }
                        )
                        if (!isImageLoading) {


                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )
                            Column(
                                modifier = Modifier
                                    .matchParentSize()
                                    .padding(dimensionResource(R.dimen.padding_medium)),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Verified Icon",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = article.sourceDto.name,
                                        color = Color.White.copy(alpha = 0.9f),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_dot),
                                        contentDescription = "Dot Icon",
                                        tint = Color.White,
                                        modifier = Modifier.size(5.dp)
                                    )
                                    Text(
                                        text = article.timeAgo,
                                        color = Color.White.copy(alpha = 0.7f),
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = article.title,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    maxLines = 2,
                                )
                            }

                        }
                    }
                }
            }
        }
        PageIndicator(
            pageCount = articleList.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}


@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 40.dp else 10.dp,
        animationSpec = tween(durationMillis = 400),
        label = ""
    )

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                )
            )
    )
}
