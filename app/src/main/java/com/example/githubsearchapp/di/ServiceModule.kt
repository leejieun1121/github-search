package com.example.githubsearchapp.di

import com.example.githubsearchapp.network.SearchRetrofit
import com.example.githubsearchapp.network.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideSearchService(): SearchService =
        SearchRetrofit.create()
}