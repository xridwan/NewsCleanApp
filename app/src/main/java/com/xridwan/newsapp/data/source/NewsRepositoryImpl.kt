package com.xridwan.newsapp.data.source

import com.xridwan.newsapp.data.source.local.LocalDataSource
import com.xridwan.newsapp.data.source.local.entity.ArticleEntity
import com.xridwan.newsapp.data.source.local.entity.NewsEntity
import com.xridwan.newsapp.data.source.remote.RemoteDataSource
import com.xridwan.newsapp.data.source.remote.network.ApiResponse
import com.xridwan.newsapp.data.source.remote.response.MainModel
import com.xridwan.newsapp.data.source.remote.response.NewsModel
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.domain.repository.NewsRepository
import com.xridwan.newsapp.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors,
) : NewsRepository {

    override fun getAllNews(): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, MainModel>() {
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getNews().map {
                    NewsEntity.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<MainModel>> {
                return remoteDataSource.getAllNews()
            }

            override suspend fun saveCallResult(data: MainModel) {
                val newsList = MainModel.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()

    override fun getAllArticle(id: String, name: String): Flow<Resource<List<Article>>> =
        object : NetworkBoundResource<List<Article>, NewsModel>() {
            override fun loadFromDB(): Flow<List<Article>> {
                return localDataSource.getArticles(name)
                    .map { ArticleEntity.mapArticleEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<NewsModel>> {
                return remoteDataSource.getArticles(id)
            }

            override suspend fun saveCallResult(data: NewsModel) {
                val articleList = NewsModel.mapArticleResponsesToEntities(data)
                localDataSource.insertArticles(articleList)
            }
        }.asFlow()

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return localDataSource.getFavoriteArticles().map {
            ArticleEntity.mapArticleEntitiesToDomain(it)
        }
    }

    override fun setFavoriteArticle(article: Article, state: Boolean) {
        val articleEntity = Article.mapArticleDomainToEntity(article)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteArticle(articleEntity, state)
        }
    }
}