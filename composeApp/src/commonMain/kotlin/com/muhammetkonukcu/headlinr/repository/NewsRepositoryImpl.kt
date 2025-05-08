package com.muhammetkonukcu.headlinr.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muhammetkonukcu.headlinr.remote.MediaStackClient
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.room.repository.SettingsLocalRepository
import com.muhammetkonukcu.headlinr.source.LatestNewsPagingSource
import com.muhammetkonukcu.headlinr.source.SearchNewsPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class NewsRepositoryImpl(
    private val client: MediaStackClient,
    private val newsLocalRepo: NewsLocalRepository,
    private val settingsRepo: SettingsLocalRepository
) : NewsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLatestNewsPager(
        categories: List<String>
    ): Flow<PagingData<Article>> {
        return settingsRepo.countryFlow()
            .flatMapLatest { country ->
                Pager(
                    config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                    pagingSourceFactory = {
                        LatestNewsPagingSource(
                            client = client,
                            localRepo = newsLocalRepo,
                            categories = categories,
                            country = country
                        )
                    }
                ).flow
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchNewsPager(
        keywords: String,
        categories: List<String>
    ): Flow<PagingData<Article>> {
        return settingsRepo.countryFlow().flatMapLatest { country ->
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = {
                    SearchNewsPagingSource(
                        client = client,
                        localRepo = newsLocalRepo,
                        keywords = keywords,
                        categories = categories,
                        country = country
                    )
                }
            ).flow
        }
    }
}