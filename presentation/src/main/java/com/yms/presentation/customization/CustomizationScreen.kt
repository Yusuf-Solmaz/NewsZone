package com.yms.presentation.customization


import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.Displayable
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender
import com.yms.presentation.R
import com.yms.presentation.customization.viewmodel.CustomizationViewModel

@Composable
fun CustomizationScreen(
    saveAppEntry: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: CustomizationViewModel = hiltViewModel()
) {

    val totalPages = 3
    val currentPage = rememberSaveable { mutableStateOf(1) }

    val pages = listOf(
        Triple(stringResource(R.string.follow_up_time_question),FollowUpTime.entries.toTypedArray(), 0),
        Triple(stringResource(R.string.age_group_question), AgeGroup.entries.toTypedArray(), 1),
        Triple(stringResource(R.string.gender_question), Gender.entries.toTypedArray(), 2)
    )

    val animatedProgress by animateFloatAsState(
        targetValue = currentPage.value.toFloat() / totalPages,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    Scaffold(
        topBar = {
            LinearProgressIndicator(
                progress = {animatedProgress},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val pageContent = pages[currentPage.value - 1]

            DynamicPageWithEnumSelection(
                viewModel = viewModel,
                pageIndex = pageContent.third,
                title = pageContent.first,
                options = pageContent.second,
                onNext = {
                    if (currentPage.value < totalPages) {
                        currentPage.value++
                    } else {
                        viewModel.savePreferences {
                            saveAppEntry()
                            navigateToHome()
                        }
                    }
                },
                onBack = {
                    if (currentPage.value > 1) {
                        currentPage.value--
                    }
                },
                showBackButton = currentPage.value > 1
            )
        }
    }
}

@Composable
fun <T> DynamicPageWithEnumSelection(
    viewModel: CustomizationViewModel,
    pageIndex: Int,
    title: String,
    options: Array<T>,
    onNext: () -> Unit,
    onBack: () -> Unit,
    showBackButton: Boolean
) where T : Enum<T>, T : Displayable {
    val uiState = viewModel.uiState.value
    val selectedOption = when (pageIndex) {
        0 -> uiState.followUpTimeSelection
        1 -> uiState.ageGroupSelection
        2 -> uiState.genderSelection
        else -> null
    } as? T

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        options.forEach { option ->
            val isSelected = selectedOption == option
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        when (pageIndex) {
                            0 -> viewModel.updateSelection(followUpTime = option as? FollowUpTime)
                            1 -> viewModel.updateSelection(ageGroup = option as? AgeGroup)
                            2 -> viewModel.updateSelection(gender = option as? Gender)
                        }
                        onNext()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = option.displayName,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (showBackButton) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text(stringResource(R.string.back))
            }
        }
    }
}

