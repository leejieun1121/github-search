package com.example.githubsearchapp.domain

import androidx.paging.map
import com.example.githubsearchapp.data.repository.SearchRepository
import com.example.githubsearchapp.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoFlowUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @DispatcherModule.DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(query: String) = searchRepository.getSearchRepos(query)
        .map { pagingData -> pagingData.map { repo -> repo.toRepoInfo() } }
        .flowOn(defaultDispatcher)
}