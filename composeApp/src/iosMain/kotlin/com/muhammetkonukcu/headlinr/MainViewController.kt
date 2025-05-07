package com.muhammetkonukcu.headlinr

import androidx.compose.ui.window.ComposeUIViewController
import com.muhammetkonukcu.headlinr.di.initKoin
import com.muhammetkonukcu.headlinr.screen.MainScreen

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    MainScreen()
}