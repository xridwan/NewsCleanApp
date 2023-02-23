package com.xridwan.newsapp.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xridwan.newsapp.domain.model.Article
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
) : Parcelable {
    companion object {
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
    }
}