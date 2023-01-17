package com.xridwan.newsapp.utils

import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import com.xridwan.newsapp.data.source.local.entity.NewsEntity
import com.xridwan.newsapp.data.source.remote.response.MainModel
import com.xridwan.newsapp.data.source.remote.response.NewsModel
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News

object DataMapper {

    fun mapResponsesToEntities(input: MainModel): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.sourceList.map {
            val news = NewsEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                url = it.url,
                category = it.category,
                language = it.language,
                country = it.country
            )
            newsList.add(news)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                id = it.id,
                name = it.name,
                description = it.description,
                url = it.url,
                category = it.category,
                language = it.language,
                country = it.country
            )
        }

    fun mapArticleResponsesToEntities(input: NewsModel): List<ArticleEntity> {
        val articleList = ArrayList<ArticleEntity>()
        input.articles.map {
            val article = ArticleEntity(
                id = it.id,
                name = it.sources.name,
                author = it.author,
                title = it.title,
                description = it.desc,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                isFavorite = false
            )
            articleList.add(article)
        }
        return articleList
    }

    fun mapArticleEntitiesToDomain(input: List<ArticleEntity>): List<Article> =
        input.map {
            Article(
                id = it.id,
                name = it.name,
                author = it.author,
                title = it.title,
                desc = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                isFavorite = it.isFavorite
            )
        }


    fun mapArticleDomainToEntity(input: Article) = ArticleEntity(
        id = input.id,
        name = input.name,
        author = input.author,
        title = input.title,
        description = input.desc,
        url = input.url,
        urlToImage = input.urlToImage,
        publishedAt = input.publishedAt,
        isFavorite = input.isFavorite
    )
}