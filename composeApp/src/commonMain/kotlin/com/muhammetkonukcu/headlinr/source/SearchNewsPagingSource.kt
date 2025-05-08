package com.muhammetkonukcu.headlinr.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muhammetkonukcu.headlinr.remote.MediaStackClient
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.room.repository.NewsLocalRepository

private const val STARTING_PAGE = 1

class SearchNewsPagingSource(
    private val client: MediaStackClient,
    private val localRepo: NewsLocalRepository,
    private val keywords: String,
    private val categories: List<String>,
    private val country: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? =
        state.anchorPosition
            ?.let { pos -> state.closestPageToPosition(pos) }
            ?.prevKey
            ?.plus(1)
            ?: state.closestPageToPosition(state.anchorPosition ?: 0)?.nextKey?.minus(1)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE
        val pageSize = params.loadSize.coerceAtMost(100)
        val offset = (page - 1) * pageSize

        return try {
            val response = client.searchNews(
                keywords   = keywords,
                categories = categories,
                country = country,
                limit      = pageSize,
                offset     = offset
            )
            val items = response.articles.map { item ->
                val fav = localRepo.isNewsFavorite(item.url)
                item.copy(isFavorite = fav)
            }
            val total = response.pagination.total

            LoadResult.Page(
                data = items,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (offset + items.size >= total) null else page + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}