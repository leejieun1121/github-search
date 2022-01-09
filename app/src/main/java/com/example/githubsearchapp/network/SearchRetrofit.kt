package com.example.githubsearchapp.network

import com.example.githubsearchapp.network.service.SearchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchRetrofit {
    private const val BASE_URL = "https://api.github.com/"

    fun create(): SearchService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchService::class.java)
    }
}