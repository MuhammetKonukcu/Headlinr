package com.muhammetkonukcu.headlinr.di

import com.muhammetkonukcu.headlinr.database.getDatabase
import com.muhammetkonukcu.headlinr.room.database.AppDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabase(get()) }
}