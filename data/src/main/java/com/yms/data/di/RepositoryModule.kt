package com.yms.data.di

import com.yms.data.repository.customization.CustomizationRepositoryImpl
import com.yms.data.repository.local.LocalSavedNewsRepositoryImpl
import com.yms.data.repository.notification.BreakingNewsNotificationRepositoryImpl
import com.yms.data.repository.remote.news.NewsRepositoryImpl
import com.yms.data.repository.summarization.ArticleSummarizationRepositoryImpl
import com.yms.domain.repository.news.BreakingNewsNotificationRepository
import com.yms.domain.repository.news.LocalSavedNewsRepository
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.repository.summarization.ArticleSummarizationRepository
import com.yms.domain.repository.user_preferences.CustomizationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCustomizationRepository(
        customizationRepositoryImpl: CustomizationRepositoryImpl
    ): CustomizationRepository

    @Binds
    @Singleton
    abstract fun bindBreakingNewsNotificationRepository(
        breakingNewsNotificationRepositoryImpl: BreakingNewsNotificationRepositoryImpl
    ): BreakingNewsNotificationRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    abstract fun bindLocalSavedNewsRepository(
        localSavedNewsRepositoryImpl: LocalSavedNewsRepositoryImpl
    ): LocalSavedNewsRepository

    @Binds
    @Singleton
    abstract fun bindArticleSummarizationRepository(
        articleSummarizationRepositoryImpl: ArticleSummarizationRepositoryImpl
    ): ArticleSummarizationRepository
}
