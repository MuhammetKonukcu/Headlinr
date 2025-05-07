package com.muhammetkonukcu.headlinr.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammetkonukcu.headlinr.room.dao.NewsDao
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}