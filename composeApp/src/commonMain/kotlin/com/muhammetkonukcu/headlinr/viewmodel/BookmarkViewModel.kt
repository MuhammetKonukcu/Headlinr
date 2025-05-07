package com.muhammetkonukcu.headlinr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val localRepo: NewsLocalRepository
) : ViewModel() {
    val favoriteNewsPaging: Flow<PagingData<NewsEntity>> =
        localRepo.getAllNews().cachedIn(viewModelScope)

    fun removeFavoriteNews(url: String) {
        viewModelScope.launch {
            localRepo.removeFavoriteNews(url)
        }
    }
}