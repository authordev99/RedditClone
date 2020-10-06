package com.teddybrothers.redditclone.utils

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RedditCloneApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RedditCloneApplication)
            modules(listOf(
                appModule
            ))
        }
    }
}