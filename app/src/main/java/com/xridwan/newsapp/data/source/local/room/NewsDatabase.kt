package com.xridwan.newsapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import com.xridwan.newsapp.data.source.local.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}