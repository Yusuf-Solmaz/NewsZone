package com.yms.newszone

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yms.newszone.component.RequestNotificationPermission
import com.yms.presentation.main.NewsZoneApp
import com.yms.presentation.settings.viewmodel.SettingsViewModel
import com.yms.utils.LocaleManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

                Toast.makeText(this, "Bildirim izni verildi", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Bildirim izni verilmedi", Toast.LENGTH_SHORT).show()
            }
        }

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

