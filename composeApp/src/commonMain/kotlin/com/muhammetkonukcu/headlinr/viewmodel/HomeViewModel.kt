package com.muhammetkonukcu.headlinr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.repository.NewsRepository
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.room.repository.SettingsLocalRepository
import com.muhammetkonukcu.headlinr.util.getCurrentTimeMillis
import kotlinx.coroutines.launch

class HomeViewModel(
    repo: NewsRepository,
    private val localRepo: NewsLocalRepository,
    private val settingsRepo: SettingsLocalRepository
) : ViewModel() {
    val countryCodeFlow = settingsRepo.countryFlow()

    val latestNews = repo
        .getLatestNewsPager(categories = listOf())
        .cachedIn(viewModelScope)

    fun onCountryChanged(newCountry: String) {
        viewModelScope.launch {
            settingsRepo.setCountry(newCountry)
        }
    }

    fun addFavoriteNews(article: Article) {
        viewModelScope.launch {
            val now: Long = getCurrentTimeMillis()
            localRepo.insertNews(
                NewsEntity(
                    createdAt = now,
                    url = article.url,
                    title = article.title,
                    source = article.source,
                    country = article.country,
                    category = article.category,
                    imageUrl = article.cleanImageUrl,
                    description = article.description,
                    publishedAt = article.publishedAt
                )
            )
        }
    }

    fun removeFavoriteNews(url: String) {
        viewModelScope.launch {
            localRepo.removeFavoriteNews(url)
        }
    }
}