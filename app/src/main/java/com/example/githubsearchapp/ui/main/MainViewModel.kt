package com.example.githubsearchapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubsearchapp.data.repository.SearchRepository
import com.example.githubsearchapp.data.vo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    fun getRepoList(query: String): Flow<PagingData<Repo>> =
        searchRepository.getSearchRepos(query)
            .cachedIn(viewModelScope)
}