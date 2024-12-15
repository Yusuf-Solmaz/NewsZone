package com.yms.data.di

import android.content.Context
import androidx.work.WorkManager
import com.yms.data.repository.customization.CustomizationRepositoryImpl
import com.yms.domain.repository.CustomizationRepository
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
}
