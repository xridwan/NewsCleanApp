package com.xridwan.newsapp.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.newsapp.databinding.ViewItemNewsBinding
import com.xridwan.newsapp.domain.model.News

class NewsAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<NewsAdapter.MainViewHolder>(), Filterable {

    private var originalList: List<News> = listOf()
    private var filteredList: List<News> = listOf()

    inner class MainViewHolder(private val binding: ViewItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News) {
            binding.apply {
                tvName.text = item.name
                tvDesc.text = item.description
                tvCategory.text = item.category
                tvCountry.text = item.country
                tvLanguage.text = item.language
            }
            itemView.setOnClickListener {
                listener.listener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ViewItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            bind(filteredList[position])
            setIsRecyclable(false)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun submitList(list: List<News>) {
        originalList = list
        filteredList = list
        notifyDataSetChanged()
    }

    interface Listener {
        fun listener(data: News)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredList = if (charString.isEmpty()) {
                    originalList
                } else {
                    originalList.filter {
                        it.name.contains(charString, ignoreCase = true)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<News>
                notifyDataSetChanged()
            }
        }
    }
}
