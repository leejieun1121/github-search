package com.example.githubsearchapp.data.dto

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("total_count") val count: Int,
    @SerializedName("incomplete_results") val result: Boolean,
    @SerializedName("items") val items: List<Repo> = emptyList(),
)

data class Repo(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val name: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("description") val description: String? = "",
    @SerializedName("stargazers_count") val starCount: Int? = 0,
    @SerializedName("language") val language: String? = "",
)

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)