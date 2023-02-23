package com.xridwan.newsapp.domain.model

import android.os.Parcelable
import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int = 0,
    val name: String,
    val author: String?,
    val title: String?,
    val desc: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val isFavorite: Boolean = false,
) : Parcelable {
    companion object {
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
}
