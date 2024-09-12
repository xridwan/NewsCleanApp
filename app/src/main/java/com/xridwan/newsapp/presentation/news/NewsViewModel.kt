package com.xridwan.newsapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.newsapp.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun news() = newsUseCase.getAllNews().asLiveData()
}