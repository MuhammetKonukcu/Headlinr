package com.muhammetkonukcu.headlinr.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val total: Int
)