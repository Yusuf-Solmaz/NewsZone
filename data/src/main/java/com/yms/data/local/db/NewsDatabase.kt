package com.yms.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yms.data.local.dao.CachedNewsDao
import com.yms.data.local.dao.SavedNewsDao
import com.yms.data.local.model.CachedNewsEntity
import com.yms.data.local.model.SavedNewsEntity

@Database(
    entities = [CachedNewsEntity::class, SavedNewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract val cachedNewsDao: CachedNewsDao
    abstract val savedNewsDao: SavedNewsDao

}