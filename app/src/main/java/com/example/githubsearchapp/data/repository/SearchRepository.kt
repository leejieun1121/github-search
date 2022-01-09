package com.example.githubsearchapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearchapp.data.paging.SearchPagingSource
import com.example.githubsearchapp.data.remote.SearchRemoteDataSource
import com.example.githubsearchapp.data.vo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val remote: SearchRemoteDataSource
){
    companion object {
        private const val PAGE_SIZE = 10
    }

    fun getRepoList(query: String): Flow<PagingData<Repo>> =
        Pager(PagingConfig(PAGE_SIZE)){
            SearchPagingSource(query,remote)
        }.flow
}