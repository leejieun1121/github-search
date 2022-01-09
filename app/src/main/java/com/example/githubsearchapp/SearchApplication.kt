package com.example.githubsearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}