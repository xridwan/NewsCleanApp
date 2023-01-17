package com.xridwan.newsapp.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MainModel(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("sources")
    val sourceList: MutableList<SourceList>
)

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