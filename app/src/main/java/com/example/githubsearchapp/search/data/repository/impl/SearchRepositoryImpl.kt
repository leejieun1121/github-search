package com.example.githubsearchapp.search.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearchapp.search.data.dto.Repo
import com.example.githubsearchapp.search.data.paging.SearchPagingSource
import com.example.githubsearchapp.search.data.repository.SearchRepository
import com.example.githubsearchapp.search.data.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val service: SearchService,
) : SearchRepository {

    companion object {
        private const val PAGE_SIZE = 10
    }

    override fun getSearchRepos(query: String): Flow<PagingData<Repo>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            SearchPagingSource(query, service)
        }.flow
}