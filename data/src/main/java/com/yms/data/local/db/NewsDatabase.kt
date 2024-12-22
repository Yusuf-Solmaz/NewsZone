package com.yms.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yms.data.local.dao.NewsDao
import com.yms.data.local.model.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {
    abstract val dao: NewsDao

}