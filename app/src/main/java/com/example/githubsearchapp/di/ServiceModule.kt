package com.example.githubsearchapp.di

import com.example.githubsearchapp.network.SearchRetrofit
import com.example.githubsearchapp.network.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideSearchService(): SearchService =
        SearchRetrofit.create()
}