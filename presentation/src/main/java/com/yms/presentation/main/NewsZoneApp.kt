package com.yms.presentation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yms.presentation.R
import com.yms.presentation.navigation.NavigationGraph
import com.yms.presentation.navigation.NewsZoneNavigation
import com.yms.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.yms.theme.NewsZoneTheme


@Composable
fun NewsZoneApp(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
//    val  currentScreen = NavigationGraph.valueOf(
//        backStackEntry?.destination?.route ?: NavigationGraph.NEWS_HOME.name
//    )
    val currentScreen = backStackEntry?.destination?.route ?: NavigationGraph.NEWS_HOME.name


    val onBoardingState by viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current

    Log.d("NewsZoneApp", "Current Screen: $onBoardingState")

    NewsZoneTheme {
        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(
                            paddingValues
                        )
                ) {
                    NewsZoneNavigation(
                        navController = navController,
                        saveAppEntry = viewModel::onEvent,
                        onBoardingState = onBoardingState
                    )
                }

            },
            bottomBar = {
                if (currentScreen == NavigationGraph.NEWS_HOME.name || currentScreen == NavigationGraph.SAVED_NEWS_SCREEN.name) {
                    BottomBar(
                        currentScreen = currentScreen,
                        onNavigate = { screen -> navController.navigate(screen.name) }
                    )
                } else if (currentScreen == NavigationGraph.ARTICLE_DETAIL_SCREEN.name) {
                    BottomBarForArticleDetail()
                }
            },
        )
    }
}


@Composable
fun BottomBar(currentScreen: String, onNavigate: (NavigationGraph) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(60.dp),
        containerColor = MaterialTheme.colorScheme.background,

        ) {
        NavigationBarItem(
            selected = currentScreen == NavigationGraph.NEWS_HOME.name,
            onClick = { onNavigate(NavigationGraph.NEWS_HOME) },
            icon = { Icon(
                Icons.Default.Home,
                contentDescription = "Home"
            ) },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.colorScheme.secondary,
                unselectedTextColor = MaterialTheme.colorScheme.secondary,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.background
            )
        )
        NavigationBarItem(
            selected = currentScreen == NavigationGraph.SAVED_NEWS_SCREEN.name,
            onClick = { onNavigate(NavigationGraph.SAVED_NEWS_SCREEN) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Saved"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = MaterialTheme.colorScheme.secondary,
                unselectedTextColor = MaterialTheme.colorScheme.secondary,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.background
            )
        )
    }
}

@Composable
fun BottomBarForArticleDetail() {
    var isDialogOpen by remember { mutableStateOf(false) }

    NavigationBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(60.dp),
        containerColor = MaterialTheme.colorScheme.background,

        ) {
        NavigationBarItem(
            selected = false,
            onClick = { isDialogOpen = true },
            icon = { Icon(Icons.Default.Build, contentDescription = "Özet Çıkar") }
        )
    }

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            title = { Text(text = "Özet Çıkar") },
            text = { Text(text = "Makalenin özetini çıkarmak istediğinize emin misiniz?") },
            confirmButton = {
                TextButton(onClick = {
                    // Özet çıkarma işlemi burada yapılabilir
                    isDialogOpen = false
                }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDialogOpen = false }) {
                    Text("Hayır")
                }
            }
        )
    }
}
