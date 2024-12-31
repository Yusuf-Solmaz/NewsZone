package com.yms.newszone

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.yms.presentation.main.NewsZoneApp
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
                NewsZoneApp()
            }
        }
    }
}


@Composable
fun ShowNotificationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Bildirim İzni") },
        text = { Text("Uygulama bildirim gönderebilmesi için izin gereklidir.") },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("İzin Ver")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("İptal")
            }
        }
    )
}

@Composable
fun RequestNotificationPermission(
    requestPermissionLauncher: ActivityResultLauncher<String>,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(true) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        if (showDialog.value) {
            Box(modifier = Modifier.fillMaxSize().alpha(0.3f)) {
                ShowNotificationDialog(
                    onDismissRequest = {
                        showDialog.value = false
                    },
                    onConfirm = {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        showDialog.value = false
                    },
                    onDismiss = {
                        Toast.makeText(context, "Bildirim izni verilmedi", Toast.LENGTH_SHORT).show()
                        showDialog.value = false
                    }
                )
            }
        }
    }

    if (!showDialog.value || ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        content()
    }
}
