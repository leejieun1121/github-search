package com.example.githubsearchapp.search.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.githubsearchapp.search.domain.RepoFlowUseCase
import com.example.githubsearchapp.search.domain.model.RepoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repoFlowUseCase: RepoFlowUseCase,
) : ViewModel() {

    private val _repoList = MutableStateFlow<PagingData<RepoInfo>>(PagingData.empty())
    val repoList = _repoList.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun getRepoList(query: String) {
        viewModelScope.launch {
            runCatching {
                repoFlowUseCase(query)
            }.onSuccess { repoFlow ->
                repoFlow.collectLatest { repoInfo ->
                    _repoList.emit(repoInfo)
                }
            }.onFailure { error ->
                _error.emit(error.toString())
            }
        }
    }

}