package com.xridwan.newsapp.domain.usecase

import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetAllNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<News>>> {
        return newsRepository.getAllNews()
    }
}

class GetAllArticles(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(id: String, name: String): Flow<Resource<List<Article>>> {
        return newsRepository.getAllArticle(id, name)
    }
}

class GetFavoriteArticles(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.getFavoriteArticles()
    }
}

class SetFavoriteArticle(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(article: Article, state: Boolean) {
        return newsRepository.setFavoriteArticle(article, state)
    }
}

data class NewsUseCase(
    val getAllNews: GetAllNews,
    val getAllArticles: GetAllArticles,
    val getFavoriteArticles: GetFavoriteArticles,
    val setFavoriteArticle: SetFavoriteArticle
)