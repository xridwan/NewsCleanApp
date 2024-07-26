package com.xridwan.newsapp.presentation.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.newsapp.databinding.NewsItemBinding
import com.xridwan.newsapp.domain.model.News
import java.util.*

//class MainAdapter(
//    private val listener: Listener
//) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
//
//    interface Listener {
//        fun listener(name: String)
//    }
//
//    inner class MainViewHolder(private val binding: NewsItemBinding) : ViewHolder(binding.root) {
//        fun bind(item: News) {
//            binding.apply {
//                tvName.text = item.name
//                tvDesc.text = item.description
//                tvCategory.text = item.category
//                tvCountry.text = item.country
//                tvLanguage.text = item.language
//            }
//            itemView.setOnClickListener {
//                listener.listener(item.name)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
//        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MainViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
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
//    private val diffCallBack = object : DiffUtil.ItemCallback<News>() {
//        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, diffCallBack)
//
//}

class MainAdapter(
    private val sourceList: MutableList<News>,
    var itemClickCallBack: OnItemClickCallBack
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>(), Filterable {

    var filterList = mutableListOf<News>()

    init {
        filterList = sourceList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getItemCount(): Int = filterList.size

    inner class MainViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sourceList: News) {
            binding.apply {
                tvName.text = sourceList.name
                tvDesc.text = sourceList.description
                tvCategory.text = sourceList.category
                tvCountry.text = sourceList.country
                tvLanguage.text = sourceList.language
            }

            itemView.setOnClickListener {
                itemClickCallBack.onItemClicked(sourceList)
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: News)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                filterList = if (charString.isEmpty()) sourceList
                else {
                    val filteredList = sourceList
                        .filter { it.name.lowercase(Locale.ROOT).contains(charString) }
                        .toMutableList()
                    filteredList
                }

                val filterResult = FilterResults()
                filterResult.values = filterList
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as MutableList<News>
                notifyDataSetChanged()
            }
        }
    }
}
