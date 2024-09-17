package com.xridwan.newsapp.utils

import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import com.xridwan.newsapp.data.source.local.entity.NewsEntity
import com.xridwan.newsapp.data.source.remote.response.Article
import com.xridwan.newsapp.data.source.remote.response.SourceList
import com.xridwan.newsapp.domain.model.News


fun SourceList.toEntityModel(): NewsEntity {
    return NewsEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        url = this.url,
        category = this.category,
        language = this.language,
        country = this.country
    )
}

fun NewsEntity.toDomainModel(): News {
    return News(
        id = this.id,
        name = this.name,
        description = this.description,
        url = this.url,
        category = this.category,
        language = this.language,
        country = this.country,
        isFavorite = false
    )
}

fun Article.toEntityModel(): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        name = this.sources.name,
        author = this.author,
        title = this.title,
        description = this.desc,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        isFavorite = false
    )
}

fun ArticleEntity.toDomainModel(): com.xridwan.newsapp.domain.model.Article {
    return com.xridwan.newsapp.domain.model.Article(
        id = this.id,
        name = this.name,
        author = this.author,
        title = this.title,
        desc = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        isFavorite = this.isFavorite
    )
}

fun com.xridwan.newsapp.domain.model.Article.toEntityModel(): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        name = this.name,
        author = this.author,
        title = this.title,
        description = this.desc,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        isFavorite = this.isFavorite
    )
}