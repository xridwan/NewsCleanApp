package com.xridwan.newsapp.domain.usecase

import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsInteractor(
    private val newsRepository: NewsRepository
) : NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<News>>> {
        return newsRepository.getAllNews()
    }

    override fun getAllArticles(id: String, name: String): Flow<Resource<List<Article>>> {
        return newsRepository.getAllArticle(id, name)
    }

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return newsRepository.getFavoriteArticles()
    }

    override fun setFavoriteArticle(article: Article, state: Boolean) {
        return newsRepository.setFavoriteArticle(article, state)
    }
}