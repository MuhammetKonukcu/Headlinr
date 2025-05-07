package com.muhammetkonukcu.headlinr.remote.response

import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.remote.entity.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaStackResponse(
    val pagination: Pagination,
    @SerialName("data") val articles: List<Article>
)