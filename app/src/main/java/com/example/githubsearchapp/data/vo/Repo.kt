package com.example.githubsearchapp.data.vo

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val name: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("description") val description: String? = "",
    @SerializedName("stargazers_count") val starCount: Int? = 0,
    @SerializedName("language") val language: String? = "",
)