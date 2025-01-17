package com.yms.newszone

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.yms.domain.repository.news.BreakingNewsNotificationRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewsZoneApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var breakingNewsNotificationRepository: BreakingNewsNotificationRepository

    override fun onCreate() {
        super.onCreate()
        breakingNewsNotificationRepository.scheduleBreakingNewsNotification()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}