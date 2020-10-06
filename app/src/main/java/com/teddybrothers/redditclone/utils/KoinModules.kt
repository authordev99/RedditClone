package com.teddybrothers.redditclone.utils

import com.teddybrothers.redditclone.viewmodels.TopicViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { TopicViewModel(get()) }
    single {
       SessionManager(get())
    }
    single { androidContext() }
}