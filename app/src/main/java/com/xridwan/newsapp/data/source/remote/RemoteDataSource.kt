package com.xridwan.newsapp.data.source.remote

import com.xridwan.newsapp.BuildConfig.API_KEY
import com.xridwan.newsapp.data.source.remote.network.ApiResponse
import com.xridwan.newsapp.data.source.remote.network.ApiService
import com.xridwan.newsapp.data.source.remote.response.MainModel
import com.xridwan.newsapp.data.source.remote.response.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService
) {
    fun getAllNews(): Flow<ApiResponse<MainModel>> {
        return flow {
            try {
                val response = apiService.getNews(API_KEY)
                val data = response.sourceList
                if (data.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getArticles(id: String): Flow<ApiResponse<NewsModel>> {
        return flow {
            try {
                val response = apiService.getArticles(id, API_KEY)
                val data = response.articles
                if (data.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}