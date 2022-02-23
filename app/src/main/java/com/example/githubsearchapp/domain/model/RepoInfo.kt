package com.example.githubsearchapp.domain.model

data class RepoInfo(
    val id: Int,
    val name: String,
    val description: String?,
    val starCount: Int?,
    val language: String?,
    val avatarUrl: String,
)