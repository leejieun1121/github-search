package com.example.githubsearchapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.githubsearchapp.R
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
            Glide.with(imgUser.context)
                .load(item.owner.avatarUrl)
                .apply(RequestOptions()
                    .transform(RoundedCorners(20))
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
                .into(imgUser)
            tvName.text = item.name
            tvLanguage.text = item.language
            tvDescription.text = item.description
            tvStarCount.text = item.starCount.toString()
        }
    }

}