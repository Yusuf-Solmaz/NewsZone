package com.yms.presentation.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
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


        composable(route = NavigationGraph.SPLASH_SCREEN.name) {
            SplashScreen(navigateToScreen = {
                navController.navigate(afterSplashScreen.startDestination){
                    popUpTo(NavigationGraph.SPLASH_SCREEN.name) {
                        inclusive = true
                    }
                }
            })
        }

        navigation(
            startDestination = NavigationGraph.NEWS_HOME.name,
            route = NavigationGraph.MAIN_CONTENT.name,
        ) {
            composable(
                route = NavigationGraph.NEWS_HOME.name,
                enterTransition = ::slideInToRight,
                exitTransition = ::slideOutToLeft)
            {
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
            composable(
                route = NavigationGraph.SEARCH_SCREEN.name) {
                SearchScreen(
                    navigateToArticleDetailScreen = { articleData ->
                        updateSharedArticle(articleData)
                        navController.navigate(NavigationGraph.ARTICLE_DETAIL_SCREEN.name)
                    }
                )
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
            SettingsScreen(
                onChangeCategory = {
                    navController.navigate(NavigationGraph.CUSTOMIZATION_SCREEN.name)
                }
            )
        }

        composable(NavigationGraph.ONBOARDING_SCREEN.name) {
            OnBoardingScreen(
                navigateToCustomization = {
                    navController.navigate(NavigationGraph.CUSTOMIZATION_SCREEN.name){
                        popUpTo(NavigationGraph.ONBOARDING_SCREEN.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable(NavigationGraph.CUSTOMIZATION_SCREEN.name) {
            CustomizationScreen(
                saveAppEntry = { saveAppEntry(OnBoardingEvent.SaveAppEntry) },
                navigateToHome = {
                    navController.navigate(NavigationGraph.NEWS_HOME.name){
                        popUpTo(NavigationGraph.CUSTOMIZATION_SCREEN.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

fun slideInToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideInToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

fun slideOutToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

fun slideOutToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}