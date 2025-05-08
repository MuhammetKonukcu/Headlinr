package com.muhammetkonukcu.headlinr.room.repository

import com.muhammetkonukcu.headlinr.room.dao.SettingsDao
import com.muhammetkonukcu.headlinr.room.entity.SettingsEntity
import com.muhammetkonukcu.headlinr.util.getCurrentCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SettingsLocalRepositoryImpl(
    private val dao: SettingsDao
) : SettingsLocalRepository {
    override fun countryFlow(): Flow<String> =
        dao.countryFlow()
            .map { it?.lowercase() ?: getCurrentCountry() }
            .distinctUntilChanged()

    override suspend fun setCountry(country: String) {
        dao.upsert(SettingsEntity(country = country))
    }
}
