package com.muhammetkonukcu.headlinr.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.muhammetkonukcu.headlinr.room.entity.NewsEntity
import com.muhammetkonukcu.headlinr.util.ErrorItem
import com.muhammetkonukcu.headlinr.util.LoadAsyncImage
import com.muhammetkonukcu.headlinr.util.LoadingItem
import com.muhammetkonukcu.headlinr.util.formatToLocalDate
import com.muhammetkonukcu.headlinr.viewmodel.BookmarkViewModel
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.add_fav
import headlinr.composeapp.generated.resources.bookmark
import headlinr.composeapp.generated.resources.ph_bookmark_fill
import headlinr.composeapp.generated.resources.ph_paper_plane_tilt
import headlinr.composeapp.generated.resources.placeholder_dark
import headlinr.composeapp.generated.resources.placeholder_light
import headlinr.composeapp.generated.resources.share
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun BookmarkScreen(navController: NavController, innerPadding: PaddingValues) {
    val viewModel = koinViewModel<BookmarkViewModel>()
    val lazyPagingItems = viewModel.favoriteNewsPaging.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.statusBarsPadding()
            .padding(bottom = innerPadding.calculateBottomPadding())
            .background(color = MaterialTheme.colorScheme.background),
        topBar = { TopAppBar() }
    ) { innerPadding ->
        BookmarkLazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            bookmarkViewModel = viewModel,
            lazyPagingItems = lazyPagingItems,
            navController = navController
        )
    }
}

@Composable
private fun TopAppBar() {
    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.bookmark),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BookmarkLazyColumn(
    modifier: Modifier,
    bookmarkViewModel: BookmarkViewModel,
    lazyPagingItems: LazyPagingItems<NewsEntity>,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    LoadingItem()
                }
            }

            is LoadState.Error -> {
                item { ErrorItem(onRetryClick = { lazyPagingItems.retry() }) }
            }

            else -> {}
        }

        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            item?.let {
                NewsEntityItem(newsEntity = item, navController = navController, onFavClicked = {
                    bookmarkViewModel.removeFavoriteNews(item.url)
                })
            }
        }

        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading -> {
                item { LoadingItem() }
            }

            is LoadState.Error -> {
                item { ErrorItem(onRetryClick = { lazyPagingItems.retry() }) }
            }

            else -> {}
        }
    }
}

@Composable
private fun NewsEntityItem(
    newsEntity: NewsEntity,
    navController: NavController,
    onFavClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(all = 8.dp)
    ) {
        Text(
            text = newsEntity.source,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = newsEntity.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        newsEntity.publishedAt?.let {
            Text(
                text = formatToLocalDate(newsEntity.publishedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val isDarkTheme = isSystemInDarkTheme()
        val placeholder =
            if (isDarkTheme) Res.drawable.placeholder_dark else Res.drawable.placeholder_light

        LoadAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
                .clip(RoundedCornerShape(size = 12.dp)),
            model = newsEntity.imageUrl,
            contentDescription = newsEntity.title,
            placeholderRes = painterResource(placeholder),
            errorRes = painterResource(placeholder),
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(Res.drawable.ph_paper_plane_tilt),
                    contentDescription = stringResource(Res.string.share),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = { onFavClicked.invoke() }) {
                Icon(
                    painter = painterResource(Res.drawable.ph_bookmark_fill),
                    contentDescription = stringResource(Res.string.add_fav),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

