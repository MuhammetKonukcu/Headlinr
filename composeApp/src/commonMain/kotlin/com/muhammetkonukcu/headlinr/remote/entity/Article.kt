package com.muhammetkonukcu.headlinr.remote.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Article(
    val url: String,
    val title: String,
    val source: String,
    val image: String? = null,
    val author: String? = null,
    val country: String? = null,
    val category: String? = null,
    val language: String? = null,
    val description: String? = null,
    @Transient var isFavorite: Boolean = false,
    @SerialName("published_at") val publishedAt: String? = null
){
    /**
     * Gerçek görüntü url’ini ilk boşluğa kadar alır
     */
    val cleanImageUrl: String?
        get() = image
            ?.substringBefore(' ')
}