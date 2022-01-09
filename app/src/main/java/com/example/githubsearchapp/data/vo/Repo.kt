package com.example.githubsearchapp.data.vo

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("full_name") val name: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("language") val language: String,
)

data class Owner(
    @SerializedName("login") val userName: String,
    @SerializedName("avatar_url") val profileUrl: String,
)

data class SearchResponse(
    @SerializedName("total_count") val count: Int,
    @SerializedName("incomplete_result") val result: Boolean,
    @SerializedName("items") val items: List<Repo>
)