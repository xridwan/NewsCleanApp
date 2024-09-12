package com.xridwan.newsapp.presentation.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.newsapp.R
import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.databinding.FragmentArticleBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.Utils.parcelable
import com.xridwan.newsapp.utils.hide
import com.xridwan.newsapp.utils.show
import com.xridwan.newsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(), ArticleAdapter.Listener {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticleViewModel by viewModels()
    private val articleAdapter by lazy { ArticleAdapter(this) }
    private var news: News? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromArgs()
        setupListeners()
    }

    private fun getDataFromArgs() {
        news = parcelable(Constant.EXTRA_DATA)
        news?.let {
            setupUI(it)
            fetchData(it)
        }
    }

    private fun setupUI(news: News) = with(binding) {
        tvName.text = news.name
        tvDesc.text = news.description
    }

    private fun fetchData(news: News) {
        viewModel.articles(news.id, news.name).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> binding.progressCircular.show()
                is Resource.Success -> handleSuccess(response.data)
                is Resource.Error -> handleError(response.message)
            }
        }
    }

    private fun handleSuccess(data: List<Article>?) = with(binding) {
        progressCircular.hide()
        data?.let {
            articleAdapter.differ.submitList(it)
            rvHeadlines.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = articleAdapter
            }
        }
    }

    private fun handleError(message: String?) {
        toast(message ?: getString(R.string.generic_error_message))
        binding.progressCircular.hide()
    }

    private fun setupListeners() = with(binding) {
        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun listener(article: Article) {
        val bundle = Bundle().apply {
            putParcelable(Constant.EXTRA_DATA, article)
        }
        findNavController().navigate(R.id.action_articleFragment_to_detailFragment, bundle)
    }
}
