package com.xridwan.newsapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getFavoriteArticles() = newsUseCase.getFavoriteArticles().asLiveData()
}