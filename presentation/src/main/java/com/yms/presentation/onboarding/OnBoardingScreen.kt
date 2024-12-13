package com.yms.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yms.presentation.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier,saveAppEntry: () -> Unit, navigateToHome: () -> Unit) {

    val pager = getOnboardingPages()
    val pagerState = rememberPagerState(initialPage = 0) {
        pager.size
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            pageContent = { page ->
                OnboardingPage(page = pager[page])
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                pageSize = pager.size,
                selectedPage = pagerState.currentPage
            )
            NextBackButton(
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
                        saveAppEntry()
                        navigateToHome()
                    }
                }
            )
        }
    }
}

@Composable
fun PageIndicator(
    pageSize: Int,
    selectedPage: Int
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) { page ->

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(20.dp)
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
    currentPage: Int,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    onGetStartedClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentPage != 0) {
            TextButton(onClick = {
                onBackClick()
            }) {
                Text(
                    text = "Back", style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
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
                )
            )
        }

    }
}
