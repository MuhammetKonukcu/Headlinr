package com.muhammetkonukcu.headlinr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.repository.NewsRepository
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository
import com.muhammetkonukcu.headlinr.util.getCurrentTimeMillis
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: NewsRepository,
    private val localRepo: NewsLocalRepository
) : ViewModel() {
    private val _keyword = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult: Flow<PagingData<Article>> = _keyword
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { kw ->
            if (kw.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repo.searchNewsPager(
                    keywords = kw,
                )
            }
        }
        .cachedIn(viewModelScope)

    private val _isNewsFavorite = MutableStateFlow<Boolean>(false)
    val isNewsFavorite: StateFlow<Boolean> = _isNewsFavorite

    fun isNewsFavorite(url: String) {
        viewModelScope.launch {
            _isNewsFavorite.value = localRepo.isNewsFavorite(url)
        }
    }

    fun addFavoriteNews(article: Article) {
        viewModelScope.launch {
            _isNewsFavorite.emit(true)
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
            _isNewsFavorite.emit(false)
            localRepo.removeFavoriteNews(url)
        }
    }

    fun updateKeyword(keyword: String) {
        viewModelScope.launch {
            _keyword.emit(keyword)
        }
    }
}