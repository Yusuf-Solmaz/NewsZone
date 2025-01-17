package com.yms.data.repository.notification

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yms.data.worker.NotificationWorker
import com.yms.domain.repository.news.BreakingNewsNotificationRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BreakingNewsNotificationRepositoryImpl @Inject constructor(
    private val workManager: WorkManager
): BreakingNewsNotificationRepository {


    override fun scheduleBreakingNewsNotification() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            1, TimeUnit.HOURS
        )
            .setInitialDelay(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()


        workManager.enqueueUniquePeriodicWork(
            NotificationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}