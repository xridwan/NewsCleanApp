package com.xridwan.newsapp.presentation.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xridwan.newsapp.domain.usecase.NewsUseCase
import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _dataList = MutableLiveData<List<Article>?>()
    val dataList: LiveData<List<Article>?> get() = _dataList

    fun getArticles(id: String, name: String) = viewModelScope.launch {
        newsUseCase.getAllArticles(id, name).collectLatest {
            when (it) {
                is Resource.Loading -> {
                    _loading.value = true
                }
                is Resource.Success -> {
                    _dataList.value = it.data
                    _loading.value = false
                }
                is Resource.Error -> {
                    Log.e("Error ViewModel", "getArticles: ${it.message}")
                    _loading.value = false
                }
            }
        }
    }

    fun invoke() = newsUseCase.getAllNews()
}