package com.yms.presentation.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yms.presentation.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier,navigateToCustomization: () -> Unit) {

    val pager = getOnboardingPages()
    val pagerState = rememberPagerState(initialPage = 0) {
        pager.size
    }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageContent = { page ->
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(pager[page].image),
                    contentDescription = null,
                )

            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter)
        )
        {
            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {

                PageIndicator(
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_big)),
                    pageSize = pager.size,
                    selectedPage = pagerState.currentPage
                )

                Text(
                    text = stringResource(pager[pagerState.currentPage].title),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 30.sp
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = stringResource(pager[pagerState.currentPage].description),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    NextBackButton(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                        currentPage = pagerState.currentPage,
                        onNextClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        onBackClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        onGetStartedClick = {
                            scope.launch {
                                navigateToCustomization()
                            }
                        }
                    )
                }
            }

        }

    }

}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int
) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) { page ->

            val width = animateDpAsState(
                targetValue = if (page == selectedPage) 40.dp else 10.dp,
                animationSpec = tween(durationMillis = 400),
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(10.dp)
                    .width(width.value)
                    .clip(CircleShape)
                    .background(
                        color = if (page == selectedPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
            ) {

            }
        }
    }
}

@Composable
fun NextBackButton(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onGetStartedClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentPage != 0) {
            TextButton(onClick = {
                onBackClick()
            }) {
                Text(
                    text = stringResource(R.string.back), style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {
                if (currentPage == 2) {
                    onGetStartedClick()
                } else {
                    onNextClick()
                }
            },
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = if (currentPage == 2) stringResource(R.string.get_started) else stringResource(
                    R.string.next
                ),
                style = MaterialTheme.typography.labelSmall
            )
        }

    }
}
