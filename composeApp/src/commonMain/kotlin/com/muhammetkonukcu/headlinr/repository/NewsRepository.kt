package com.muhammetkonukcu.headlinr.repository

import androidx.paging.PagingData
import com.muhammetkonukcu.headlinr.remote.entity.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getLatestNewsPager(
        categories: List<String> = emptyList()
    ): Flow<PagingData<Article>>

    fun searchNewsPager(
        keywords: String,
        categories: List<String> = emptyList()
    ): Flow<PagingData<Article>>
}