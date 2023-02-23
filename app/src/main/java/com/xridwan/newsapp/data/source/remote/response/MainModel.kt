package com.xridwan.newsapp.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.xridwan.newsapp.data.source.local.entity.NewsEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainModel(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("sources")
    val sourceList: MutableList<SourceList>
): Parcelable{
    companion object{
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
    }
}

@Parcelize
data class SourceList(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("language")
    val language: String,

    @field:SerializedName("country")
    val country: String
) : Parcelable