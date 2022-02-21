package com.example.githubsearchapp.data.repository

import androidx.paging.PagingData
import com.example.githubsearchapp.data.dto.Repo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchRepos(query: String): Flow<PagingData<Repo>>

}