package com.example.githubsearchapp.data.vo

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count") val count: Int,
    @SerializedName("incomplete_results") val result: Boolean,
    @SerializedName("items") val items: List<Repo> = emptyList(),
)