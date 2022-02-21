package com.example.githubsearchapp.domain

import com.example.githubsearchapp.data.dto.Repo
import com.example.githubsearchapp.domain.model.RepoInfo

fun Repo.toRepoInfo() = RepoInfo(
    id = id,
    name = name,
    description = description,
    starCount = starCount,
    language = language,
    avatarUrl = owner.avatarUrl
)

