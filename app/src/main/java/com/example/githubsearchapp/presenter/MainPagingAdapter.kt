package com.example.githubsearchapp.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchapp.databinding.ItemMainBinding
import com.example.githubsearchapp.domain.model.RepoInfo

class MainPagingAdapter :
    PagingDataAdapter<RepoInfo, MainPagingAdapter.MainViewHolder>(DIFF_CALLBACK) {
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
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class MainViewHolder(
        val binding: ItemMainBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    private fun MainViewHolder.bind(item: RepoInfo) {
        binding.apply {
            repoInfo = item
            executePendingBindings()
        }
    }

}