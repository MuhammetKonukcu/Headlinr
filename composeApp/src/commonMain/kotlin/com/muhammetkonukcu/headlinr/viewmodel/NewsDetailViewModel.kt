package com.muhammetkonukcu.headlinr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.util.getCurrentTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsDetailViewModel(private val localRepo: NewsLocalRepository) : ViewModel() {
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun checkIfFavorite(url: String) {
        viewModelScope.launch {
            val fav = localRepo.isNewsFavorite(url)
            _isFavorite.value = fav
        }
    }

    fun updateFavStatus(value: Boolean) {
        viewModelScope.launch {
            _isFavorite.emit(value)
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