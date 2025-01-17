package com.yms.data.di

import com.yms.domain.repository.news.LocalSavedNewsRepository
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.repository.summarization.ArticleSummarizationRepository
import com.yms.domain.repository.user_preferences.CustomizationRepository
import com.yms.domain.repository.user_preferences.UserPreferencesRepository
import com.yms.domain.usecase.gemini.GetSummary
import com.yms.domain.usecase.gemini.SummaryUseCase
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
import com.yms.domain.usecase.user_preferences.settings.ReadDarkMode
import com.yms.domain.usecase.user_preferences.settings.ReadLanguage
import com.yms.domain.usecase.user_preferences.settings.SaveDarkMode
import com.yms.domain.usecase.user_preferences.settings.SaveLanguage
import com.yms.domain.usecase.user_preferences.settings.SettingsPreferencesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    fun provideUserPreferencesUseCase(userPreferencesRepository: UserPreferencesRepository): UserPreferencesUseCase {
        return UserPreferencesUseCase(
            saveAppEntry = SaveAppEntry(userPreferencesRepository),
            readAppEntry = ReadAppEntry(userPreferencesRepository)
        )
    }

    @Provides
    fun provideCustomizationPreferencesUseCase(userPreferencesRepository: UserPreferencesRepository,customizationRepository: CustomizationRepository): CustomizationPreferencesUseCase {
        return CustomizationPreferencesUseCase(SaveCategory(customizationRepository),ReadCategory(userPreferencesRepository))
    }

    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCase(
            SearchNews(newsRepository),
            GetBreakingNews(newsRepository),
            GetNewsByMediator(newsRepository)
        )
    }

    @Provides
    fun provideSavedNewsUseCase(localSavedNewsRepository: LocalSavedNewsRepository): SavedNewsUseCase{
        return SavedNewsUseCase(
            getSavedNews = GetSavedNews(localSavedNewsRepository),
            deleteSavedNews = DeleteSavedNews(localSavedNewsRepository),
            isNewsSaved = IsNewsSaved(localSavedNewsRepository),
            insertSavedNews = InsertSavedNews(localSavedNewsRepository)
        )
    }

    @Provides
    fun provideSettingsUseCase(userPreferencesRepository: UserPreferencesRepository): SettingsPreferencesUseCase{
        return SettingsPreferencesUseCase(
            readLanguage = ReadLanguage(userPreferencesRepository),
            saveLanguage = SaveLanguage(userPreferencesRepository),
            saveDarkMode = SaveDarkMode(userPreferencesRepository),
            readDarkMode = ReadDarkMode(userPreferencesRepository)
        )
    }


    @Provides
    fun provideSummaryUseCase(articleSummarizationRepository: ArticleSummarizationRepository): SummaryUseCase {
        return SummaryUseCase(
            getSummary = GetSummary(articleSummarizationRepository)
        )
    }

}