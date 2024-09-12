package com.xridwan.newsapp.domain.di

import com.xridwan.newsapp.data.source.NewsRepositoryImpl
import com.xridwan.newsapp.data.source.local.LocalDataSource
import com.xridwan.newsapp.data.source.remote.RemoteDataSource
import com.xridwan.newsapp.domain.repository.NewsRepository
import com.xridwan.newsapp.domain.usecase.GetAllArticles
import com.xridwan.newsapp.domain.usecase.GetAllNews
import com.xridwan.newsapp.domain.usecase.GetFavoriteArticles
import com.xridwan.newsapp.domain.usecase.NewsUseCase
import com.xridwan.newsapp.domain.usecase.SetFavoriteArticle
import com.xridwan.newsapp.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        appExecutors: AppExecutors
    ): NewsRepository {
        return NewsRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            appExecutors = appExecutors
        )
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: NewsRepository): NewsUseCase = NewsUseCase(
        getAllNews = GetAllNews(repository),
        getAllArticles = GetAllArticles(repository),
        getFavoriteArticles = GetFavoriteArticles(repository),
        setFavoriteArticle = SetFavoriteArticle(repository)
    )
}