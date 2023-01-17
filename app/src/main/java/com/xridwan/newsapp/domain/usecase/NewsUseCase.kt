package com.xridwan.newsapp.domain.usecase

import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>
    fun getAllArticles(id: String, name: String): Flow<Resource<List<Article>>>
    fun getFavoriteArticles(): Flow<List<Article>>
    fun setFavoriteArticle(article: Article, state: Boolean)
}