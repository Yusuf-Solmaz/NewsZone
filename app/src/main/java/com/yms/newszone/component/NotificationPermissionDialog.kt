package com.yms.newszone.component

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun ShowNotificationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Önemli Hatırlatma") },
        text = { Text("Uygulamadan en etkili bir şekilde yararlanmanız için bildirimleri açmanızı öneriririz.") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text("Tamam")
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