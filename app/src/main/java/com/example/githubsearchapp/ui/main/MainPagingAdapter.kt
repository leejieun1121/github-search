package com.example.githubsearchapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchapp.data.vo.Repo
import com.example.githubsearchapp.databinding.ItemMainBinding

class MainPagingAdapter : PagingDataAdapter<Repo, MainPagingAdapter.MainViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(
                oldItem: Repo,
                newItem: Repo,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Repo,
                newItem: Repo,
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

    private fun MainViewHolder.bind(item: Repo) {
        binding.apply {
            repo = item
            executePendingBindings()
        }
    }

}