package com.xridwan.newsapp.data.source.remote.network

import com.xridwan.newsapp.data.source.remote.response.MainModel
import com.xridwan.newsapp.data.source.remote.response.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("sources")
    suspend fun getNews(
        @Query("apiKey") apiKey: String? = null
    ): MainModel

    @GET("top-headlines")
    suspend fun getArticles(
        @Query("sources") id: String? = null,
        @Query("apiKey") apiKey: String? = null
    ): NewsModel
}