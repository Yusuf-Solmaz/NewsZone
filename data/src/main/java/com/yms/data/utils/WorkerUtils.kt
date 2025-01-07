package com.yms.data.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.yms.domain.R
import com.yms.domain.model.news.ArticleData

const val FOLLOW_UP_TIME_KEY = "follow_up_time"
const val AGE_GROUP_KEY = "age_group"
const val GENDER_KEY = "gender"
const val WORKER_ERROR = "worker_error"
const val OUTPUT_CATEGORY_KEY = "output_category_key"

fun makeStatusNotification(articleData: ArticleData, context: Context) {


    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bg)
            .setContentTitle(context.getString(R.string.breaking_news))
            .setContentText(articleData.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 500, 1000, 500))

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }
}