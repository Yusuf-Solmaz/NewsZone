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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yms.presentation.R
import com.yms.presentation.home.viewmodel.BreakingNewsState
import kotlinx.coroutines.delay

@Composable
fun BreakingNewsSection(modifier: Modifier = Modifier, breakingNewsState: BreakingNewsState) {
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
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            )
            Text(stringResource(R.string.view_all), style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground))
        }

        if (breakingNewsState.isLoading){
            Text(text = "Loading...")
        }
        else if (breakingNewsState.error != null){
            Text(text = breakingNewsState.error)
        }
        else{
            BreakingNewsCard(breakingNewsState = breakingNewsState)
        }

    }
}

@Composable
fun BreakingNewsCard(modifier: Modifier = Modifier, breakingNewsState: BreakingNewsState) {

    val pagerState = rememberPagerState(
        pageCount = { breakingNewsState.news?.size ?: 0 }
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
                val article = breakingNewsState.news?.get(currentPage) ?: return@HorizontalPager

                Card(
                    modifier = Modifier.wrapContentSize()
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
                                .height(250.dp),
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
                        if (!isImageLoading){



                            Box(
                                modifier = Modifier.matchParentSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )
                            Column(
                                modifier = Modifier.matchParentSize()
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
            pageCount = breakingNewsState.news?.size ?: 0,
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
        label = "")

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    )
}

@Preview(showBackground = true)
@Composable
fun BreakingNewsSectionPreview() {
    BreakingNewsSection(breakingNewsState = BreakingNewsState())
}