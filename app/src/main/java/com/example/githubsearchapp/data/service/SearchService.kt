package com.example.githubsearchapp.data.service

import com.example.githubsearchapp.data.dto.RepoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    suspend fun getSearchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): RepoDto
}