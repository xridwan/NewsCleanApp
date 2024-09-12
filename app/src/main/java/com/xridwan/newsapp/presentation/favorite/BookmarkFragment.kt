package com.xridwan.newsapp.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.newsapp.R
import com.xridwan.newsapp.databinding.FragmentBookmarkBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.utils.Constant
import com.xridwan.newsapp.utils.hide
import com.xridwan.newsapp.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(), BookmarkAdapter.Listener, View.OnClickListener {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private val bookmarkAdapter by lazy { BookmarkAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadFavoriteArticles()
    }

    private fun setupUI() = with(binding) {
        tvName.text = getString(R.string.title_favorite)
        ivBack.setOnClickListener(this@BookmarkFragment)
    }

    private fun loadFavoriteArticles() {
        bookmarkViewModel.getFavoriteArticles().observe(viewLifecycleOwner) { articles ->
            if (articles.isNullOrEmpty()) {
                showEmptyState()
            } else {
                showArticles(articles)
            }
        }
    }

    private fun showEmptyState() = with(binding) {
        rvFavorite.hide()
        layoutEmpty.linearEmpty.show()
    }

    private fun showArticles(articles: List<Article>) = with(binding) {
        bookmarkAdapter.setData(articles)
        rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookmarkAdapter
        }
        layoutEmpty.linearEmpty.hide()
    }

    override fun listener(article: Article) {
        val bundle = Bundle().apply {
            putParcelable(Constant.EXTRA_DATA, article)
        }
        findNavController().navigate(R.id.action_bookmarkFragment_to_detailFragment, bundle)
    }

    override fun onClick(view: View?) {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}