package com.xridwan.newsapp.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun articles(id: String, name: String) = newsUseCase.getAllArticles(id, name).asLiveData()
}