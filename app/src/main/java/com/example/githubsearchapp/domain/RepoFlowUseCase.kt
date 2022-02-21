package com.example.githubsearchapp.domain

import androidx.paging.map
import com.example.githubsearchapp.data.repository.SearchRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoFlowUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(query: String) = searchRepository.getSearchRepos(query)
        .map { pagingData -> pagingData.map { repo -> repo.toRepoInfo() } }
}