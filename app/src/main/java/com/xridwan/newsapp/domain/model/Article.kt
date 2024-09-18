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
) : Parcelable
