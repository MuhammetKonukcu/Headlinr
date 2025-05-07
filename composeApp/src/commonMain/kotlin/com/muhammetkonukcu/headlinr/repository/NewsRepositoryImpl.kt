package com.muhammetkonukcu.headlinr.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muhammetkonukcu.headlinr.remote.MediaStackClient
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.source.LatestNewsPagingSource
import com.muhammetkonukcu.headlinr.source.SearchNewsPagingSource
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val client: MediaStackClient,
    private val localRepo: NewsLocalRepository
) : NewsRepository {

    override fun getLatestNewsPager(
        categories: List<String>
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                LatestNewsPagingSource(
                    client = client,
                    localRepo = localRepo,
                    categories = categories
                )
            }
        ).flow
    }

    override fun searchNewsPager(
        keywords: String,
        categories: List<String>
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    client     = client,
                    localRepo = localRepo,
                    keywords   = keywords,
                    categories = categories
                )
            }
        ).flow
    }
}