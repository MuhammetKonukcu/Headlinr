package com.muhammetkonukcu.headlinr.room.repository

import kotlinx.coroutines.flow.Flow

interface SettingsLocalRepository {
    fun countryFlow(): Flow<String>
    suspend fun setCountry(country: String)
}
