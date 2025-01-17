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

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCustomizationRepository(
        customizationRepositoryImpl: CustomizationRepositoryImpl
    ): CustomizationRepository

    @Binds
    abstract fun bindBreakingNewsNotificationRepository(
        breakingNewsNotificationRepositoryImpl: BreakingNewsNotificationRepositoryImpl
    ): BreakingNewsNotificationRepository

    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    abstract fun bindLocalSavedNewsRepository(
        localSavedNewsRepositoryImpl: LocalSavedNewsRepositoryImpl
    ): LocalSavedNewsRepository

    @Binds
    abstract fun bindArticleSummarizationRepository(
        articleSummarizationRepositoryImpl: ArticleSummarizationRepositoryImpl
    ): ArticleSummarizationRepository
}
