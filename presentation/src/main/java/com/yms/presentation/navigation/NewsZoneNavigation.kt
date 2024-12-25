package com.yms.presentation.navigation



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yms.presentation.article_detail.ArticleDetailScreen
import com.yms.presentation.customization.CustomizationScreen
import com.yms.presentation.home.NewsHomeScreen
import com.yms.presentation.onboarding.OnBoardingScreen
import com.yms.presentation.onboarding.viewmodel.OnBoardingEvent
import com.yms.presentation.onboarding.viewmodel.OnBoardingState
import com.yms.presentation.saved_news.SavedNews
import com.yms.presentation.search.SearchScreen
import com.yms.presentation.settings.SettingsScreen
import com.yms.presentation.splash.SplashScreen
import com.yms.utils.SharedViewModel

@Composable
fun NewsZoneNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    saveAppEntry: (OnBoardingEvent) -> Unit,
    onBoardingState: OnBoardingState
) {

    val sharedViewModel: SharedViewModel = hiltViewModel()

    if (onBoardingState.isSplashScreenVisible) {
        SplashScreen()
    } else if (onBoardingState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = onBoardingState.startDestination
        ) {
            navigation(
                startDestination = NavigationGraph.NEWS_HOME.name,
                route = NavigationGraph.MAIN_CONTENT.name
            ){
                composable(NavigationGraph.NEWS_HOME.name) {
                    entry->

                    NewsHomeScreen(
                        navigateToSearchScreen = {
                            navController.navigate(NavigationGraph.SEARCH_SCREEN.name)
                        },
                        navigateToArticleDetailScreen = {
                            articleData ->
                            sharedViewModel.updateState(articleData)
                            navController.navigate(NavigationGraph.ARTICLE_DETAIL_SCREEN.name)
                        }
                    )
                }
                composable(NavigationGraph.SEARCH_SCREEN.name) {
                    SearchScreen()
                }
                composable(NavigationGraph.ARTICLE_DETAIL_SCREEN.name){
                    val state by sharedViewModel.sharedState.collectAsStateWithLifecycle()

                    ArticleDetailScreen(
                        sharedState = state,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

            }

            composable(NavigationGraph.SAVED_NEWS_SCREEN.name){
                SavedNews()
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
            composable(NavigationGraph.SPLASH_SCREEN.name) {
                SplashScreen()
            }
        }
    }
}
