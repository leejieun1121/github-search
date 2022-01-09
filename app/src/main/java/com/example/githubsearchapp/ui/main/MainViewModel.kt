package com.example.githubsearchapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.githubsearchapp.data.repository.SearchRepository
import com.example.githubsearchapp.data.vo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    lateinit var repoList: Flow<PagingData<Repo>>

    fun getRepoList(query:String){
        repoList = searchRepository.getRepoList(query)
    }

}