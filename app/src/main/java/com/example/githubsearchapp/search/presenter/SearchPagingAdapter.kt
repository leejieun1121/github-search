package com.example.githubsearchapp.search.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchapp.R
import com.example.githubsearchapp.databinding.ItemMainBinding
import com.example.githubsearchapp.search.domain.model.RepoInfo

class MainPagingAdapter
    : PagingDataAdapter<RepoInfo, MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepoInfo>() {
            override fun areItemsTheSame(
                oldItem: RepoInfo,
                newItem: RepoInfo,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RepoInfo,
                newItem: RepoInfo,
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_main,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class MainViewHolder(
    private val binding: ItemMainBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RepoInfo) {
        binding.apply {
            repoInfo = item
            executePendingBindings()
        }
    }
}
