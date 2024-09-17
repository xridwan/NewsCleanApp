package com.xridwan.newsapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xridwan.newsapp.R
import com.xridwan.newsapp.databinding.FragmentDetailBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.Utils.parcelable
import com.xridwan.newsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailArticleViewModel by viewModels()
    private var isFavorite: Boolean = false
    private var article: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = parcelable(Constant.EXTRA_DATA)
        isFavorite = article?.isFavorite == true

        setupUI()
        setArticleContent(article?.url.orEmpty())
        updateBookmarkState(isFavorite)
    }

    private fun setupUI() = with(binding) {
        tvName.text = getString(R.string.title_detail)
        ivBack.setOnClickListener(this@DetailFragment)
        ivBookmark.setOnClickListener(this@DetailFragment)
    }

    private fun setArticleContent(url: String) = with(binding) {
        wvArticle.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            isVerticalScrollBarEnabled = true
            loadUrl(url)
            webChromeClient = WebChromeClient()
        }
    }

    private fun updateBookmarkState(isFavorite: Boolean) {
        val bookmarkDrawable =
            if (isFavorite) R.drawable.ic_bookmark_true else R.drawable.ic_bookmark_false
        binding.ivBookmark.background =
            ContextCompat.getDrawable(requireContext(), bookmarkDrawable)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> findNavController().navigateUp()
            R.id.iv_bookmark -> toggleFavoriteState()
        }
    }

    private fun toggleFavoriteState() {
        isFavorite = !isFavorite
        article?.let {
            detailViewModel.setFavoriteArticle(it, isFavorite)
        }
        updateBookmarkState(isFavorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
