package com.xridwan.newsapp.presentation.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xridwan.newsapp.R
import com.xridwan.newsapp.databinding.ActivityDetailArticleBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.Utils.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailArticleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailArticleBinding
    private val detailArticleViewModel: DetailArticleViewModel by viewModels()
    private var state: Boolean = false
    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromIntent()
        setContent(article?.url.toString())
        binding.tvName.text = getString(R.string.title_detail)
        binding.ivBack.setOnClickListener(this)
        binding.ivBookmark.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressedDispatcher.onBackPressed()
            }
            R.id.iv_bookmark -> {
                state = !state
                article?.let { detailArticleViewModel.setFavoriteArticle(it, state) }
                isFavorite(state)
            }
        }
    }

    private fun getDataFromIntent() {
        article = intent.parcelable(Constant.EXTRA_DATA)
        state = article?.isFavorite == true
        isFavorite(state)
    }

    private fun setContent(url: String) {
        binding.apply {
            wvArticle.webViewClient = WebViewClient()
            wvArticle.settings.javaScriptEnabled = true
            wvArticle.isVerticalScrollBarEnabled = true
            wvArticle.loadUrl(url)
            wvArticle.webChromeClient = WebChromeClient()
        }
    }

    private fun isFavorite(state: Boolean) {
        if (state) binding.ivBookmark.background =
            ContextCompat.getDrawable(this, R.drawable.ic_bookmark_true)
        else binding.ivBookmark.background =
            ContextCompat.getDrawable(this, R.drawable.ic_bookmark_false)
    }
}