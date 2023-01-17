package com.xridwan.newsapp.data.source.local.room

import androidx.room.*
import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import com.xridwan.newsapp.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("SELECT * FROM article where name LIKE :name")
    fun getArticles(name: String): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(news: List<ArticleEntity>)

    @Query("SELECT * FROM article where is_favorite = 1")
    fun getFavoriteArticles(): Flow<List<ArticleEntity>>

    @Update
    fun updateFavoriteArticle(articleEntity: ArticleEntity)
}