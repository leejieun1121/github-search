package com.example.githubsearchapp.data.remote

import com.example.githubsearchapp.network.service.SearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRemoteDataSource @Inject constructor(private val service: SearchService) {

    suspend fun getSearchRepos(query: String, perPage: Int, page: Int) =
        service.getSearchRepos(query, perPage, page)
}