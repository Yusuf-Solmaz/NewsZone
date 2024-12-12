package com.yms.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository{
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
}