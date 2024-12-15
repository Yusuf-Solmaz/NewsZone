package com.yms.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yms.presentation.home.viewmodel.NewsHomeViewModel

@Composable
fun NewsHomeScreen(viewModel: NewsHomeViewModel = hiltViewModel()){

    val categoryState by viewModel.categoryState.collectAsState()
    val newsState by viewModel.newsState.collectAsState()

    Log.d("NewsHomeScreen", "NewsHomeScreen: ${newsState.news}")

    if (newsState.isLoading){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Loading")
        }
    }else {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Text(categoryState.category)

            Text(newsState.news.size.toString())

        }
    }
}