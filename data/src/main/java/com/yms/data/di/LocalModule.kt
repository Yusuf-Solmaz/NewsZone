package com.yms.data.di

import android.content.Context
import androidx.room.Room
import com.yms.data.local.dao.CachedNewsDao
import com.yms.data.local.dao.SavedNewsDao
import com.yms.data.local.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideNewsDatabase( @ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCachedNewsDao(newsDatabase: NewsDatabase): CachedNewsDao = newsDatabase.cachedNewsDao

    @Provides
    @Singleton
    fun provideSavedNewsDao(newsDatabase: NewsDatabase): SavedNewsDao = newsDatabase.savedNewsDao
}