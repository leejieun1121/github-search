package com.example.githubsearchapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubsearchapp.domain.RepoFlowUseCase
import com.example.githubsearchapp.domain.model.RepoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoFlowUseCase: RepoFlowUseCase,
) : ViewModel() {

    fun getRepoList(query: String): Flow<PagingData<RepoInfo>> =
        repoFlowUseCase(query)
            .cachedIn(viewModelScope)
}