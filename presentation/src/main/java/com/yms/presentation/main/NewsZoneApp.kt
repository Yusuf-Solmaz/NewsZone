package com.yms.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yms.presentation.navigation.NavigationGraph
import com.yms.presentation.navigation.NewsZoneNavigation
import com.yms.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.yms.theme.NewsZoneTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsZoneApp(viewModel: OnBoardingViewModel = hiltViewModel(),navController: NavHostController = rememberNavController()){

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavigationGraph.valueOf(
        backStackEntry?.destination?.route ?: NavigationGraph.NEWS_HOME.name
    )

    val onBoardingState by viewModel.uiState.collectAsState()

    Log.d("NewsZoneApp", "Current Screen: $onBoardingState")

    NewsZoneTheme {
        Scaffold(
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
                ) {
                    NewsZoneNavigation(
                        navController = navController,
                        saveAppEntry = viewModel::onEvent,
                        onBoardingState = onBoardingState
                    )
                }

            }
        )
    }

}