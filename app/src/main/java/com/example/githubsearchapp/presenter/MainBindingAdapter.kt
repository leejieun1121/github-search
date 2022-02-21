package com.example.githubsearchapp.presenter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.githubsearchapp.R
import com.example.githubsearchapp.domain.model.RepoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: PagingData<RepoInfo>?) {
    CoroutineScope(Dispatchers.IO).launch {
        list?.let {
            (adapter as? MainPagingAdapter)?.submitData(it)
        }
    }
}

@BindingAdapter("bindImage")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        Glide.with(context)
            .load(imgUrl)
            .apply(RequestOptions()
                .transform(RoundedCorners(20))
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(this)
    }
}