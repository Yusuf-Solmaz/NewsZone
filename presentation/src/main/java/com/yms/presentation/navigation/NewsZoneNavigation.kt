package com.yms.presentation.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yms.domain.model.news.BaseArticle
import com.yms.presentation.article_detail.ArticleDetailScreen
import com.yms.presentation.customization.CustomizationScreen
import com.yms.presentation.home.NewsHomeScreen
import com.yms.presentation.onboarding.OnBoardingScreen
import com.yms.presentation.onboarding.viewmodel.OnBoardingEvent
import com.yms.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.yms.presentation.saved_news.SavedNews
import com.yms.presentation.search.SearchScreen
import com.yms.presentation.settings.SettingsScreen
import com.yms.presentation.splash.SplashScreen
import com.yms.utils.SharedArticleState


@Composable
fun NewsZoneNavigation(
    navController: NavHostController,
    saveAppEntry: (OnBoardingEvent) -> Unit,
    updateSharedArticle: (BaseArticle) -> Unit,
    sharedArticleState: SharedArticleState,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {

    val afterSplashScreen by onBoardingViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = NavigationGraph.SPLASH_SCREEN.name,
    ) {

        composable(NavigationGraph.SPLASH_SCREEN.name) {
            SplashScreen(navigateToScreen = {
                navController.navigate(afterSplashScreen.startDestination)
            })
        }

        navigation(
            startDestination = NavigationGraph.NEWS_HOME.name,
            route = NavigationGraph.MAIN_CONTENT.name
        ) {
            composable(NavigationGraph.NEWS_HOME.name) {
                NewsHomeScreen(
                    navigateToSearchScreen = {
                        navController.navigate(NavigationGraph.SEARCH_SCREEN.name)
                    },
                    navigateToArticleDetailScreen = { articleData ->
                        updateSharedArticle(articleData)
                        navController.navigate(NavigationGraph.ARTICLE_DETAIL_SCREEN.name)
                    }
                )
            }
            composable(NavigationGraph.SEARCH_SCREEN.name) {
                SearchScreen()
            }
        }

        composable(NavigationGraph.ARTICLE_DETAIL_SCREEN.name) {
            ArticleDetailScreen(
                sharedArticleState = sharedArticleState,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationGraph.SAVED_NEWS_SCREEN.name) {
            SavedNews(
                savedArticleToArticleDetail = { articleData ->
                    updateSharedArticle(articleData)
                    navController.navigate(NavigationGraph.ARTICLE_DETAIL_SCREEN.name)
                }
            )
        }

        composable(NavigationGraph.SETTINGS_SCREEN.name) {
            SettingsScreen()
        }

        composable(NavigationGraph.ONBOARDING_SCREEN.name) {
            OnBoardingScreen(
                modifier = Modifier.fillMaxSize(),
                navigateToCustomization = {
                    navController.navigate(NavigationGraph.CUSTOMIZATION_SCREEN.name)
                }
            )
        }


        composable(NavigationGraph.CUSTOMIZATION_SCREEN.name) {
            CustomizationScreen(
                saveAppEntry = { saveAppEntry(OnBoardingEvent.SaveAppEntry) },
                navigateToHome = { navController.navigate(NavigationGraph.NEWS_HOME.name) }
            )
        }
    }
}

