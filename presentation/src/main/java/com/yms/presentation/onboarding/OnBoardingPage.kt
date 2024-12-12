package com.yms.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yms.presentation.R
import com.yms.theme.Typography

@Composable
fun OnboardingPage(modifier: Modifier = Modifier ,page: Page) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = stringResource(page.title),
            style = Typography.titleLarge,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = stringResource(page.description),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}



@Preview(showBackground = true)
@Composable
fun OnboardingPagePreview() {
    OnboardingPage(modifier = Modifier.fillMaxSize(),page = Page(R.string.onboarding_title_1, R.string.onboarding_description_1))
}