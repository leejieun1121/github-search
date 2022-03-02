package com.example.githubsearchapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubsearchapp.domain.RepoFlowUseCase
import com.example.githubsearchapp.domain.model.RepoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repoFlowUseCase: RepoFlowUseCase,
) : ViewModel() {

    private val _repoList = MutableStateFlow<PagingData<RepoInfo>>(PagingData.empty())
    val repoList = _repoList.asStateFlow()

    fun getRepoList(query: String) {
        viewModelScope.launch {
            repoFlowUseCase(query)
                .cachedIn(viewModelScope)
                .collectLatest { _repoList.emit(it) }
        }
    }
}