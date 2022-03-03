package com.example.githubsearchapp.search.di

import com.example.githubsearchapp.search.data.repository.SearchRepository
import com.example.githubsearchapp.search.data.repository.impl.SearchRepositoryImpl
import com.example.githubsearchapp.search.data.service.SearchService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    //repository가 인터페이스, 인터페이스 구현체를 여기서 만들어서 usecase에 주입
    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    companion object {
        @Provides
        @ViewModelScoped
        fun provideSearchService(): SearchService = //SearchRepositoryImpl에 service를 주입
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchService::class.java)

        private const val BASE_URL = "https://api.github.com/"
    }

}