package com.xridwan.newsapp.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: MutableList<Article>,
) : Parcelable

@Parcelize
data class Article(

    val id: Int = 0,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("description")
    val desc: String?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("urlToImage")
    val urlToImage: String?,

    @field:SerializedName("publishedAt")
    val publishedAt: String?,

    @field:SerializedName("source")
    val sources: Sources,
) : Parcelable

@Parcelize
data class Sources(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("name")
    val name: String,
) : Parcelable



