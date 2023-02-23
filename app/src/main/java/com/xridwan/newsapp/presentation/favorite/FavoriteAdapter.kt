package com.xridwan.newsapp.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xridwan.newsapp.R
import com.xridwan.newsapp.databinding.HeadlinesItemBinding
import com.xridwan.newsapp.domain.model.Article
import com.xridwan.newsapp.utils.Utils

//class FavoriteAdapter(
//    private val listener: Listener
//) : RecyclerView.Adapter<FavoriteAdapter.ArticleViewHolder>() {
//
//    interface Listener {
//        fun listener(article: Article)
//    }
//
//    inner class ArticleViewHolder(private val binding: HeadlinesItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: Article) {
//            binding.apply {
//                tvTitle.text = item.title
//                tvDate.text = Utils.formatDate(item.publishedAt)
//                Picasso.get().load(item.urlToImage).into(imgNews)
//
//                val state = item.isFavorite
//                if (state) ivBookmark.background =
//                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_bookmark_true)
//                else ivBookmark.background =
//                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_bookmark_false)
//            }
//            itemView.setOnClickListener {
//                listener.listener(item)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
//        val binding =
//            HeadlinesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ArticleViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
//        holder.apply {
//            bind(differ.currentList[position])
//            setIsRecyclable(false)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    private val diffCallBack = object : DiffUtil.ItemCallback<Article>() {
//        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, diffCallBack)
//
//}

class FavoriteAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<FavoriteAdapter.ArticleViewHolder>() {

    interface Listener {
        fun listener(article: Article)
    }

    private var articleList = ArrayList<Article>()

    fun setData(articleList: List<Article>) {
        val diffCallBack = DiffCallBack(this.articleList, articleList)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.articleList = articleList as ArrayList<Article>
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ArticleViewHolder(private val binding: HeadlinesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.apply {
                tvTitle.text = item.title
                tvDate.text = Utils.formatDate(item.publishedAt)
                Picasso.get().load(item.urlToImage).into(imgNews)

                val state = item.isFavorite
                if (state) ivBookmark.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_bookmark_true)
                else ivBookmark.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_bookmark_false)
            }
            itemView.setOnClickListener {
                listener.listener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            HeadlinesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class DiffCallBack(
        private val mOldDataList: List<Article>,
        private val mNewDataList: List<Article>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return mOldDataList.size
        }

        override fun getNewListSize(): Int {
            return mNewDataList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldDataList[oldItemPosition].id == mNewDataList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldArticle = mOldDataList[oldItemPosition]
            val newArticle = mNewDataList[newItemPosition]
            return oldArticle.id == newArticle.id
        }
    }
}
