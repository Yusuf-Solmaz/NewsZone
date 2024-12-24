package com.yms.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yms.data.local.dao.CachedNewsDao
import com.yms.data.local.model.CachedNewsEntity

@Database(
    entities = [CachedNewsEntity::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {
    abstract val cachedNewsDao: CachedNewsDao

}