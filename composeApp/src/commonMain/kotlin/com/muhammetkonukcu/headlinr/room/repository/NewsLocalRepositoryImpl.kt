package com.muhammetkonukcu.headlinr.room.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import com.muhammetkonukcu.headlinr.room.dao.NewsDao
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

class NewsLocalRepositoryImpl(private val newsDao: NewsDao): NewsLocalRepository {
    override suspend fun insertNews(entity: NewsEntity) = newsDao.insertNews(entity)

    override suspend fun isNewsFavorite(url: String): Boolean = newsDao.getNews(url)

    override suspend fun removeFavoriteNews(url: String) = newsDao.removeFavoriteNews(url)

    override fun getAllNews(pageSize: Int): Flow<PagingData<NewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { newsDao.getAllNewsPaging() }
        ).flow
    }
}