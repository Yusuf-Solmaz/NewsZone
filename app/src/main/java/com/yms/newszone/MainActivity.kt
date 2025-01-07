package com.yms.newszone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yms.newszone.component.RequestNotificationPermission
import com.yms.presentation.main.NewsZoneApp
import com.yms.presentation.settings.SettingsViewModel
import com.yms.utils.LocaleManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RequestNotificationPermission(requestPermissionLauncher) {
                val viewModel: SettingsViewModel = hiltViewModel()


                viewModel.languageState.collectAsStateWithLifecycle().value.language.let { language ->
                    LocaleManager.changeAppLanguage(this, language)
                }

                NewsZoneApp()
            }
        }
    }
}

