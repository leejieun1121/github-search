package com.example.githubsearchapp.search.domain

import com.example.githubsearchapp.search.data.dto.Repo
import com.example.githubsearchapp.search.domain.model.RepoInfo

fun Repo.toRepoInfo() = RepoInfo(
    id = id,
    name = name,
    description = description ?: "",
    starCount = starCount ?: 0,
    language = language ?: "",
    avatarUrl = owner.avatarUrl
)

