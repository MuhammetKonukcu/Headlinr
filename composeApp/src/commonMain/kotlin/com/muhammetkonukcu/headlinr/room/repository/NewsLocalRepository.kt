package com.muhammetkonukcu.headlinr.room.repository

import app.cash.paging.PagingData
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsLocalRepository {
    suspend fun insertNews(entity: NewsEntity)
    suspend fun isNewsFavorite(url: String): Boolean
    suspend fun removeFavoriteNews(url: String)
    fun getAllNews(pageSize: Int = 20): Flow<PagingData<NewsEntity>>
}