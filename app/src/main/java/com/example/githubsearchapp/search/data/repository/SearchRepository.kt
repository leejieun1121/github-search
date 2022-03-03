package com.example.githubsearchapp.search.data.repository

import androidx.paging.PagingData
import com.example.githubsearchapp.search.data.dto.Repo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchRepos(query: String): Flow<PagingData<Repo>>

}