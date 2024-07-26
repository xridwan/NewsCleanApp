package com.xridwan.newsapp.data.source.local

import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import com.xridwan.newsapp.data.source.local.entity.NewsEntity
import com.xridwan.newsapp.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val newsDao: NewsDao
) {
    fun getNews(): Flow<List<NewsEntity>> = newsDao.getNews()

    fun getArticles(name: String): Flow<List<ArticleEntity>> = newsDao.getArticles(name)

    fun getFavoriteArticles(): Flow<List<ArticleEntity>> = newsDao.getFavoriteArticles()

    suspend fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)

    suspend fun insertArticles(articleList: List<ArticleEntity>) =
        newsDao.insertArticles(articleList)

    fun setFavoriteArticle(articleEntity: ArticleEntity, newState: Boolean) {
        articleEntity.isFavorite = newState
        newsDao.updateFavoriteArticle(articleEntity)
    }
}