package com.example.githubsearchapp.network.service

import com.example.githubsearchapp.data.vo.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
    ): SearchResponse
}