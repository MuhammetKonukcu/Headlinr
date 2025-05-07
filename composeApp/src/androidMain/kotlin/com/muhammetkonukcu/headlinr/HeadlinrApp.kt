package com.muhammetkonukcu.headlinr

import android.app.Application
import com.muhammetkonukcu.headlinr.di.initKoin
import org.koin.android.ext.koin.androidContext

class HeadlinrApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@HeadlinrApp)
        }
    }
}