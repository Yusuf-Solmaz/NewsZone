package com.yms.data.di

import android.content.Context
import androidx.work.WorkManager
import com.yms.data.local.dao.SavedNewsDao
import com.yms.data.local.db.NewsDatabase
import com.yms.data.remote.NewsApi
import com.yms.data.repository.customization.CustomizationRepositoryImpl
import com.yms.data.repository.local.LocalSavedNewsRepositoryImpl
import com.yms.data.repository.notification.BreakingNewsNotificationRepositoryImpl
import com.yms.data.repository.remote.news.NewsRepositoryImpl
import com.yms.data.repository.summarization.ArticleSummarizationRepositoryImpl
import com.yms.domain.repository.news.LocalSavedNewsRepository
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.repository.summarization.ArticleSummarizationRepository
import com.yms.domain.repository.user_preferences.CustomizationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCustomizationRepository(
        @ApplicationContext context: Context,
        workManager: WorkManager
    ): CustomizationRepository {
        return CustomizationRepositoryImpl(context, workManager)
    }

    @Provides
    @Singleton
    fun provideBreakingNewsNotificationRepository(
        workManager: WorkManager
    ):BreakingNewsNotificationRepositoryImpl{
        return BreakingNewsNotificationRepositoryImpl(workManager)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api:NewsApi,newsDatabase: NewsDatabase): NewsRepository {
        return NewsRepositoryImpl(api,newsDatabase)
    }

    @Provides
    @Singleton
    fun provideLocalSavedNewsRepositoryImpl(savedNewsDao: SavedNewsDao): LocalSavedNewsRepository {
        return LocalSavedNewsRepositoryImpl(savedNewsDao)
    }

    @Provides
    @Singleton
    fun provideArticleSummarizationRepositoryImpl(@ApplicationContext context: Context): ArticleSummarizationRepository {
        return ArticleSummarizationRepositoryImpl(context)
    }
}
