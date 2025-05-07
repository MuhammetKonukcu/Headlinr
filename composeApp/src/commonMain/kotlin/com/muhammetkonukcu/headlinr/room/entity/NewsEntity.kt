package com.muhammetkonukcu.headlinr.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val url: String,
    val title: String,
    val source: String,
    val createdAt: Long,
    val country: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val publishedAt: String? = null
)
