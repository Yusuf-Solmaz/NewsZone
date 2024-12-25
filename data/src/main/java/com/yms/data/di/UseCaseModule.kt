package com.yms.data.di

import com.yms.domain.repository.news.LocalSavedNewsRepository
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.repository.user_preferences.CustomizationRepository
import com.yms.domain.repository.user_preferences.UserPreferencesRepository
import com.yms.domain.usecase.news.GetBreakingNews
import com.yms.domain.usecase.news.GetNewsByMediator
import com.yms.domain.usecase.news.NewsUseCase
import com.yms.domain.usecase.news.SearchNews
import com.yms.domain.usecase.saved_news.DeleteSavedNews
import com.yms.domain.usecase.saved_news.GetSavedNews
import com.yms.domain.usecase.saved_news.InsertSavedNews
import com.yms.domain.usecase.saved_news.IsNewsSaved
import com.yms.domain.usecase.saved_news.SavedNewsUseCase
import com.yms.domain.usecase.user_preferences.app_entry.ReadAppEntry
import com.yms.domain.usecase.user_preferences.app_entry.SaveAppEntry
import com.yms.domain.usecase.user_preferences.app_entry.UserPreferencesUseCase
import com.yms.domain.usecase.user_preferences.category.CustomizationPreferencesUseCase
import com.yms.domain.usecase.user_preferences.category.ReadCategory
import com.yms.domain.usecase.user_preferences.category.SaveCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideUserPreferencesUseCase(userPreferencesRepository: UserPreferencesRepository): UserPreferencesUseCase {
        return UserPreferencesUseCase(
            saveAppEntry = SaveAppEntry(userPreferencesRepository),
            readAppEntry = ReadAppEntry(userPreferencesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCustomizationPreferencesUseCase(userPreferencesRepository: UserPreferencesRepository,customizationRepository: CustomizationRepository): CustomizationPreferencesUseCase {
        return CustomizationPreferencesUseCase(SaveCategory(customizationRepository),ReadCategory(userPreferencesRepository))
    }

    @Provides
    @Singleton
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCase(
            SearchNews(newsRepository),
            GetBreakingNews(newsRepository),
            GetNewsByMediator(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSavedNewsUseCase(localSavedNewsRepository: LocalSavedNewsRepository): SavedNewsUseCase{
        return SavedNewsUseCase(
            getSavedNews = GetSavedNews(localSavedNewsRepository),
            deleteSavedNews = DeleteSavedNews(localSavedNewsRepository),
            isNewsSaved = IsNewsSaved(localSavedNewsRepository),
            insertSavedNews = InsertSavedNews(localSavedNewsRepository)
        )

    }

}