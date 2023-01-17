package com.xridwan.newsapp.presentation.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.newsapp.databinding.ActivityArticleBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.presentation.detail.DetailArticleActivity
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.hide
import com.xridwan.newsapp.utils.show
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class ArticleActivity : AppCompatActivity(), View.OnClickListener, ArticleAdapter.Listener {

    private lateinit var binding: ActivityArticleBinding
    private val viewModel: ArticleViewModel by viewModels()
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener(this)

        val news = intent.getParcelableExtra<News>(Constant.EXTRA_DATA)
        if (news != null) {
            setContent(news)
            getData(news)
        }
    }

    private fun setContent(news: News?) {
        binding.apply {
            tvName.text = news?.name
            tvDesc.text = news?.description
        }
    }

    private fun getData(news: News) {
        viewModel.getArticles(news.id, news.name)
        viewModel.loading.observe(this) {
            if (it) binding.progressCircular.show()
            else binding.progressCircular.hide()
        }
        viewModel.dataList.observe(this) {
            if (it.isNullOrEmpty()) {
                binding.layoutEmpty.linearEmpty.show()
            } else
                articleAdapter.differ.submitList(it)
            binding.rvHeadlines.apply {
                layoutManager = LinearLayoutManager(this@ArticleActivity)
                adapter = articleAdapter
            }
        }
//        viewModel.getArticles(source).observe(this) { response ->
//            if (response != null) {
//                when (response) {
//                    is Resource.Loading -> {
//                        binding.progressCircular.show()
//                    }
//                    is Resource.Success -> {
//                        val data = response.data
//                        articleAdapter.differ.submitList(data)
//                        binding.rvHeadlines.apply {
//                            layoutManager = LinearLayoutManager(this@ArticleActivity)
//                            adapter = articleAdapter
//                        }
//                        binding.progressCircular.hide()
//                    }
//                    is Resource.Error -> {
//                        toast("Error..")
//                        toast(response.message.toString())
//                        binding.progressCircular.hide()
//                    }
//                }
//            }
//        }
    }

    override fun onClick(v: View?) {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun listener(article: Article) {
        startActivity(
            Intent(this, DetailArticleActivity::class.java)
                .putExtra(Constant.EXTRA_DATA, article)
        )
    }
}