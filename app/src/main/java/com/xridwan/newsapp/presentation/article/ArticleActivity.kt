package com.xridwan.newsapp.presentation.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.databinding.ActivityArticleBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.presentation.detail.DetailArticleActivity
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.Utils.parcelable
import com.xridwan.newsapp.utils.hide
import com.xridwan.newsapp.utils.show
import com.xridwan.newsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity(), View.OnClickListener, ArticleAdapter.Listener {

    private lateinit var binding: ActivityArticleBinding
    private val viewModel: ArticleViewModel by viewModels()
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(this) }
    private var news: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromIntent()
        binding.ivBack.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getDataFromIntent() {
        news = intent.parcelable(Constant.EXTRA_DATA)
        news?.let {
            setContent(it)
            getData()
        }
    }

    private fun setContent(news: News) {
        binding.apply {
            tvName.text = news.name
            tvDesc.text = news.description
        }
    }

    private fun getData() {
        news?.let {
            val id = it.id
            val name = it.name

            viewModel.articles(id, name).observe(this) { response ->
                when (response) {
                    is Resource.Loading -> {
                        binding.progressCircular.show()
                    }
                    is Resource.Success -> {
                        response.data?.let { data ->
                            articleAdapter.differ.submitList(data)
                            binding.rvHeadlines.apply {
                                layoutManager = LinearLayoutManager(this@ArticleActivity)
                                adapter = articleAdapter
                            }
                        }
                        binding.progressCircular.hide()
                    }
                    is Resource.Error -> {
                        toast("Error..")
                        toast(response.message.toString())
                        binding.progressCircular.hide()
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun listener(article: Article) {
        startActivity(
            Intent(this, DetailArticleActivity::class.java).apply {
                putExtra(Constant.EXTRA_DATA, article)
            }
        )
    }
}
