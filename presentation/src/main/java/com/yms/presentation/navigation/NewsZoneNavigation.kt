package com.yms.presentation.navigation



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yms.presentation.home.HomeScreen
import com.yms.presentation.onboarding.OnBoardingScreen
import com.yms.presentation.onboarding.viewmodel.OnBoardingEvent
import com.yms.presentation.onboarding.viewmodel.OnBoardingState
import com.yms.presentation.splash.SplashScreen

@Composable
fun NewsZoneNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    saveAppEntry: (OnBoardingEvent) -> Unit,
    onBoardingState: OnBoardingState
){

    if (onBoardingState.isSplashScreenVisible){
        SplashScreen()
    }
    else if (onBoardingState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    }
    else {
        NavHost(
            navController = navController,
            startDestination = onBoardingState.startDestination
        ){
            composable(NavigationGraph.ONBOARDING_SCREEN.name){
                OnBoardingScreen(
                    saveAppEntry = {
                        saveAppEntry(OnBoardingEvent.SaveAppEntry)
                    },
                    navigateToHome = {
                        navController.navigate(NavigationGraph.HOME.name)
                    }
                )
            }
            composable(NavigationGraph.SPLASH_SCREEN.name){
                SplashScreen()
            }
            composable(NavigationGraph.HOME.name){
                HomeScreen()
            }
        }
    }

}