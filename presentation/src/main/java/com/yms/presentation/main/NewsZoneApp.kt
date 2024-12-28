package com.yms.presentation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.yms.utils.SharedViewModel


@Composable
fun NewsZoneApp(
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreenTitle = NavigationGraph.valueOf(
        backStackEntry?.destination?.route ?: NavigationGraph.NEWS_HOME.name
    )
    val currentScreen = backStackEntry?.destination?.route ?: NavigationGraph.NEWS_HOME.name


    val onBoardingState by onBoardingViewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current

    val sharedArticleState by sharedViewModel.sharedArticleState.collectAsState()

    NewsZoneTheme {
        Scaffold(
            topBar = {
                if (currentScreen == NavigationGraph.SAVED_NEWS_SCREEN.name
                    || currentScreen == NavigationGraph.SEARCH_SCREEN.name
                    || currentScreen == NavigationGraph.NEWS_HOME.name
                ) {
                    TopBar(title = stringResource(currentScreenTitle.title ?: R.string.home),
                        isBackEnabled = currentScreen != NavigationGraph.NEWS_HOME.name,
                        navigateBack = { navController.popBackStack() })
                    Log.d("NewsZoneApp", "TopBar: $currentScreen")
                }

            },
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
                        saveAppEntry = onBoardingViewModel::onEvent,
                        onBoardingState = onBoardingState,
                        updateSharedArticle = sharedViewModel::updateState,
                        sharedArticleState = sharedArticleState
                    )
                }

            },
            bottomBar = {
                when (currentScreen) {
                    NavigationGraph.NEWS_HOME.name, NavigationGraph.SAVED_NEWS_SCREEN.name -> {
                        BottomBar(
                            currentScreen = currentScreen,
                            onNavigate = { screen -> navController.navigate(screen.name) }
                        )
                    }

                    NavigationGraph.ARTICLE_DETAIL_SCREEN.name -> {
                        BottomBarWithSheet(
                            articleContent = sharedArticleState.article?.content ?: ""
                        )
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, isBackEnabled: Boolean, navigateBack: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navigationIcon = {
            if (isBackEnabled) {
                IconButton(
                    onClick = navigateBack
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Menu",
                        modifier = Modifier.size(26.dp),
                    )
                }
            }
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Settings",
                modifier = Modifier.size(26.dp)
            )
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
            rememberTopAppBarState()
        ),
        windowInsets = TopAppBarDefaults.windowInsets
    )
}

@Composable
fun BottomBar(currentScreen: String, onNavigate: (NavigationGraph) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(60.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        listOf(
            NavigationBarItemData(
                selected = currentScreen == NavigationGraph.NEWS_HOME.name,
                icon = Icons.Default.Home,
                contentDescription = "Home",
                onClick = { onNavigate(NavigationGraph.NEWS_HOME) }
            ),
            NavigationBarItemData(
                selected = currentScreen == NavigationGraph.SAVED_NEWS_SCREEN.name,
                iconPainter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "Saved",
                onClick = { onNavigate(NavigationGraph.SAVED_NEWS_SCREEN) }
            )
        ).forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = item.onClick,
                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = item.contentDescription,
                            modifier = Modifier.size(26.dp)
                        )
                    } ?: run {
                        Icon(
                            painter = item.iconPainter!!,
                            contentDescription = item.contentDescription,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedIconColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarWithSheet(articleContent: String) {
    var isSheetOpen by remember { mutableStateOf(false) }

    NavigationBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(60.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { isSheetOpen = true },
            icon = {
                Icon(
                    Icons.Default.Build,
                    contentDescription = "Özet Çıkar",
                    modifier = Modifier.size(26.dp)
                )
            }
        )
    }

    // BottomSheetContent
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = articleContent,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

data class NavigationBarItemData(
    val selected: Boolean,
    val icon: ImageVector? = null,
    val iconPainter: Painter? = null,
    val contentDescription: String,
    val onClick: () -> Unit
)

