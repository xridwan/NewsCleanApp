package com.xridwan.newsapp.presentation.detail

import androidx.lifecycle.ViewModel
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun setFavoriteArticle(article: Article, newStatus: Boolean) =
        newsUseCase.setFavoriteArticle(article, newStatus)
}