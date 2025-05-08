package com.muhammetkonukcu.headlinr.di

import com.muhammetkonukcu.headlinr.remote.MediaStackClient
import com.muhammetkonukcu.headlinr.repository.NewsRepository
import com.muhammetkonukcu.headlinr.repository.NewsRepositoryImpl
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.room.repository.SettingsLocalRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

fun remoteModule(apiKey: String): Module = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
    single {
        MediaStackClient(
            apiKey = apiKey,
            httpClient = get(),
        )
    }
}

fun repositoryModule(): Module = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            get<MediaStackClient>(),
            get<NewsLocalRepository>(),
            get<SettingsLocalRepository>()
        )
    }
}