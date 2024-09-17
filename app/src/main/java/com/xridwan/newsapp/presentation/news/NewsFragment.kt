package com.xridwan.newsapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.newsapp.R
import com.xridwan.newsapp.data.source.Resource
import com.xridwan.newsapp.databinding.FragmentNewsBinding
import com.xridwan.newsapp.domain.model.News
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.hide
import com.xridwan.newsapp.utils.show
import com.xridwan.newsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.Listener, View.OnClickListener {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() = with(binding) {
        rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        etSearch.doOnTextChanged { text, _, _, _ ->
            ivCancel.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
            filterSearchText(text.toString())
        }

        ivCancel.setOnClickListener(this@NewsFragment)
        ivBookmark.setOnClickListener(this@NewsFragment)
    }

    private fun observeViewModel() {
        newsViewModel.news().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> binding.progressCircular.show()
                is Resource.Success -> handleSuccess(response.data)
                is Resource.Error -> handleError(response.message)
            }
        }
    }

    private fun handleSuccess(data: List<News>?) = with(binding) {
        progressCircular.hide()
        if (data.isNullOrEmpty()) {
            layoutEmpty.linearEmpty.show()
        } else {
            newsAdapter.submitList(data)
        }
    }

    private fun handleError(message: String?) {
        toast(message ?: getString(R.string.generic_error_message))
        binding.progressCircular.hide()
    }

    private fun filterSearchText(searchText: String = "") {
        try {
            newsAdapter.filter.filter(searchText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun listener(data: News) {
        val bundle = Bundle().apply {
            putParcelable(Constant.EXTRA_DATA, data)
        }
        findNavController().navigate(R.id.action_newsFragment_to_articleFragment, bundle)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_cancel -> {
                binding.etSearch.setText("")
                filterSearchText()
            }

            R.id.iv_bookmark -> {
                findNavController().navigate(R.id.action_newsFragment_to_bookmarkFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
