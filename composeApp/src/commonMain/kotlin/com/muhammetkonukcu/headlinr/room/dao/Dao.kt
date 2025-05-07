package com.muhammetkonukcu.headlinr.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.cash.paging.PagingSource
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(entity: NewsEntity)

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 
            FROM news 
            WHERE url = :url
        )
    """
    )
    suspend fun getNews(url: String): Boolean

    @Query("DELETE FROM news WHERE url = :url")
    suspend fun removeFavoriteNews(url: String)

    @Query("SELECT * FROM news ORDER BY createdAt DESC")
    fun getAllNewsPaging(): PagingSource<Int, NewsEntity>
}