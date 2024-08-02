package com.xridwan.newsapp.presentation.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.newsapp.R
import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.databinding.ActivityMainBinding
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.presentation.article.ArticleActivity
import com.xridwan.newsapp.presentation.favorite.FavoriteActivity
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.hide
import com.xridwan.newsapp.utils.show
import com.xridwan.newsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener, MainAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }

    override fun listener(data: News) {
        startActivity(
            Intent(this, ArticleActivity::class.java)
                .putExtra(Constant.EXTRA_DATA, data)
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_cancel -> {
                binding.etSearch.setText("")
                filterSearchText()
            }

            R.id.iv_bookmark -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
    }

    private fun setupUI() {
        binding.apply {
            etSearch.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    ivCancel.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                    if (it.isNotEmpty()) {
                        filterSearchText(it.toString())
                    }
                }
            }
            ivCancel.setOnClickListener(this@MainActivity)
            ivBookmark.setOnClickListener(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        mainViewModel.news().observe(this) { response ->
            response?.let {
                when (it) {
                    is Resource.Loading -> binding.progressCircular.show()
                    is Resource.Success -> handleSuccess(it.data)
                    is Resource.Error -> handleError(it.message)
                }
            }
        }
    }

    private fun handleSuccess(data: List<News>?) {
        binding.progressCircular.hide()
        if (data.isNullOrEmpty()) {
            binding.layoutEmpty.linearEmpty.show()
        } else {
            mainAdapter = MainAdapter(this)
            mainAdapter.submitList(data)
            binding.rvNews.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = mainAdapter
            }
        }
    }

    private fun handleError(message: String?) {
        toast(message.toString())
        binding.progressCircular.hide()
    }

    private fun filterSearchText(searchText: String = "") {
        try {
            mainAdapter.filter.filter(searchText)
        } catch (e: Exception) {
            e.localizedMessage
        }
    }
}
