package com.example.githubsearchapp.data.remote

import com.example.githubsearchapp.network.service.SearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRemoteDataSource @Inject constructor(private val service: SearchService) {

    suspend fun getRepoList(query: String, perPage: Int, page: Int) =
        service.getRepositories(query, perPage, page)
}