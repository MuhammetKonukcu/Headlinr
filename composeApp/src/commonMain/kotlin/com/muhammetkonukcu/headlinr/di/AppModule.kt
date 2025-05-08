package com.muhammetkonukcu.headlinr.di

import com.muhammetkonukcu.headlinr.BuildKonfig
import com.muhammetkonukcu.headlinr.room.dao.NewsDao
import com.muhammetkonukcu.headlinr.room.dao.SettingsDao
import com.muhammetkonukcu.headlinr.room.database.AppDatabase
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepositoryImpl
import com.muhammetkonukcu.headlinr.room.repository.SettingsLocalRepository
import com.muhammetkonukcu.headlinr.room.repository.SettingsLocalRepositoryImpl
import com.muhammetkonukcu.headlinr.viewmodel.BookmarkViewModel
import com.muhammetkonukcu.headlinr.viewmodel.HomeViewModel
import com.muhammetkonukcu.headlinr.viewmodel.SearchViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun appModule(): Module = module {
    single<HomeViewModel> { HomeViewModel(get(), get(), get()) }
    single<BookmarkViewModel> { BookmarkViewModel(get()) }
    single<SearchViewModel> { SearchViewModel(get(), get()) }
}

fun localRepositoryModule(): Module = module {
    single<NewsDao>     { get<AppDatabase>().getNewsDao() }
    single<SettingsDao> { get<AppDatabase>().getSettingsDao() }

    single<NewsLocalRepository>     { NewsLocalRepositoryImpl(get()) }
    single<SettingsLocalRepository> { SettingsLocalRepositoryImpl(get()) }
}


expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)

        modules(
            appModule(),
            platformModule(),
            repositoryModule(),
            localRepositoryModule(),
            remoteModule(apiKey = BuildKonfig.news_auth_token)
        )
    }