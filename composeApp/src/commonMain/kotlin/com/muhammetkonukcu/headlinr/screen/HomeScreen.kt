package com.muhammetkonukcu.headlinr.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.screen.sheet.FlagSheet
import com.muhammetkonukcu.headlinr.util.ErrorItem
import com.muhammetkonukcu.headlinr.util.LoadAsyncImage
import com.muhammetkonukcu.headlinr.util.LoadingItem
import com.muhammetkonukcu.headlinr.util.formatToLocalDate
import com.muhammetkonukcu.headlinr.util.getFlag
import com.muhammetkonukcu.headlinr.viewmodel.HomeViewModel
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.add_fav
import headlinr.composeapp.generated.resources.country_turkey
import headlinr.composeapp.generated.resources.ph_bookmark
import headlinr.composeapp.generated.resources.ph_bookmark_fill
import headlinr.composeapp.generated.resources.ph_paper_plane_tilt
import headlinr.composeapp.generated.resources.placeholder_dark
import headlinr.composeapp.generated.resources.placeholder_light
import headlinr.composeapp.generated.resources.share
import io.ktor.http.encodeURLParameter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(navController: NavController, innerPadding: PaddingValues) {
    val viewModel = koinViewModel<HomeViewModel>()
    var currentCountryCode = viewModel.countryCodeFlow.collectAsState(initial = null)
    val articles = viewModel.latestNews.collectAsLazyPagingItems()
    Scaffold(
        modifier = Modifier.padding(innerPadding),
        topBar = {
            HomeTopBar(
                countryCode = currentCountryCode.value,
                countryChanged = { newValue ->
                    viewModel.onCountryChanged(newCountry = newValue)
                })
        }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            ArticlesLazyColumn(
                lazyPagingItems = articles,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
fun ArticlesLazyColumn(
    lazyPagingItems: LazyPagingItems<Article>,
    viewModel: HomeViewModel,
    navController: NavController
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
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
                ArticleItem(
                    article = item,
                    onFavClicked = { value ->
                        if (value)
                            viewModel.addFavoriteNews(item)
                        else
                            viewModel.removeFavoriteNews(item.url)
                    },
                    onItemClicked = {
                        val rawJson = Json.encodeToString(item)
                        val safeJson = rawJson.encodeURLParameter()
                        navController.navigate("NewsDetail/$safeJson") {
                            launchSingleTop = true
                        }
                    }
                )
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
fun ArticleItem(article: Article, onFavClicked: (Boolean) -> Unit, onItemClicked: () -> Unit) {
    var isFavorite by remember { mutableStateOf(article.isFavorite) }
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(all = 8.dp)
            .clickable { onItemClicked.invoke() }
    ) {
        Text(
            text = article.source,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        article.publishedAt?.let {
            Text(
                text = formatToLocalDate(article.publishedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!article.cleanImageUrl.isNullOrBlank()) {
            val isDarkTheme = isSystemInDarkTheme()
            val placeholder =
                if (isDarkTheme) Res.drawable.placeholder_dark else Res.drawable.placeholder_light

            LoadAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
                    .clip(RoundedCornerShape(size = 12.dp)),
                model = article.cleanImageUrl,
                contentDescription = article.title,
                placeholderRes = painterResource(placeholder),
                errorRes = painterResource(placeholder),
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(Res.drawable.ph_paper_plane_tilt),
                    contentDescription = stringResource(Res.string.share),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = {
                isFavorite = !isFavorite
                article.isFavorite = isFavorite
                onFavClicked.invoke(isFavorite)
            }) {
                val iconRes = if (isFavorite) Res.drawable.ph_bookmark_fill
                else Res.drawable.ph_bookmark
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = stringResource(Res.string.add_fav),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun HomeTopBar(countryCode: String?, countryChanged: (String) -> Unit) {
    var isSheetOpen by remember { mutableStateOf(false) }
    countryCode?.let {
        val currentFlag = getFlag(countryCode)
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .clickable { isSheetOpen = true },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape),
                painter = painterResource(currentFlag.imageRes),
                contentDescription = stringResource(Res.string.country_turkey),
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(currentFlag.countryName),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (isSheetOpen) {
            FlagSheet(
                onSelectedFlag = { newCode ->
                    countryChanged.invoke(newCode)
                },
                onDismissSheet = {
                    isSheetOpen = false
                }
            )
        }
    } ?: run {
        Box(modifier = Modifier.height(40.dp))
    }
}