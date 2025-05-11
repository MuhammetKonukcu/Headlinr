package com.muhammetkonukcu.headlinr.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.theme.Blue500
import com.muhammetkonukcu.headlinr.theme.White
import com.muhammetkonukcu.headlinr.util.LoadAsyncImage
import com.muhammetkonukcu.headlinr.util.formatToLocalDate
import com.muhammetkonukcu.headlinr.viewmodel.NewsDetailViewModel
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.add_fav
import headlinr.composeapp.generated.resources.back
import headlinr.composeapp.generated.resources.news_detail
import headlinr.composeapp.generated.resources.open_in_browser
import headlinr.composeapp.generated.resources.ph_bookmark
import headlinr.composeapp.generated.resources.ph_bookmark_fill
import headlinr.composeapp.generated.resources.ph_caret_left
import headlinr.composeapp.generated.resources.placeholder_dark
import headlinr.composeapp.generated.resources.placeholder_light
import io.ktor.http.decodeURLQueryComponent
import io.ktor.http.encodeURLParameter
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NewsDetailScreen(
    navController: NavController,
    articleStr: String
) {
    val json = articleStr.decodeURLQueryComponent()
    val article: Article = Json.decodeFromString(json)
    val viewModel = koinViewModel<NewsDetailViewModel>()
    val isFavorite = viewModel.isFavorite.collectAsState()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DetailTopBar(
                navController = navController,
                isFavorite = isFavorite.value,
                favClicked = {
                    if (isFavorite.value) {
                        viewModel.updateFavStatus(false)
                        viewModel.removeFavoriteNews(article.url)
                    } else {
                        viewModel.updateFavStatus(true)
                        viewModel.addFavoriteNews(article)
                    }
                }
            )
        },
        bottomBar = { DetailBottomBar(url = article.url) }
    ) { innerPadding ->
        DetailArticleColumn(
            navController = navController,
            innerPadding = innerPadding,
            article = article
        )
    }

    LaunchedEffect(article.url) {
        viewModel.checkIfFavorite(article.url)
    }
}

@Composable
private fun DetailTopBar(
    navController: NavController,
    isFavorite: Boolean,
    favClicked: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ph_caret_left),
                    contentDescription = stringResource(Res.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = stringResource(Res.string.news_detail),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }

        val favBtnRes = if (isFavorite) Res.drawable.ph_bookmark_fill else Res.drawable.ph_bookmark

        IconButton(
            modifier = Modifier,
            onClick = { favClicked.invoke() }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(favBtnRes),
                contentDescription = stringResource(Res.string.add_fav),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun DetailBottomBar(url: String) {
    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = Blue500,
                contentColor = White,
                disabledContainerColor = Blue500.copy(alpha = 0.4f),
                disabledContentColor = White.copy(alpha = 0.4f)
            ),
            onClick = { uriHandler.openUri(url) }) {
            Text(
                text = stringResource(Res.string.open_in_browser),
                color = White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun DetailArticleColumn(
    navController: NavController,
    innerPadding: PaddingValues,
    article: Article
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 12.dp,
                end = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = article.title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        article.description?.let {
            Text(
                text = article.description,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        val isDarkTheme = isSystemInDarkTheme()
        val placeholder =
            if (isDarkTheme) Res.drawable.placeholder_dark else Res.drawable.placeholder_light

        if (!article.cleanImageUrl.isNullOrBlank()) {
            LoadAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
                    .clip(RoundedCornerShape(size = 12.dp))
                    .clickable {
                        val imageUrl = article.cleanImageUrl!!.encodeURLParameter()
                        navController.navigate(route = "ImageDetail/$imageUrl") {
                            launchSingleTop = true
                        }
                    },
                model = article.cleanImageUrl,
                contentDescription = article.title,
                placeholderRes = painterResource(placeholder),
                errorRes = painterResource(placeholder),
            )
        }

        val detailStr = when {
            article.source.isNotEmpty() && !article.publishedAt.isNullOrBlank() -> {
                "${article.source}: ${formatToLocalDate(article.publishedAt)}"
            }

            article.source.isNotEmpty() -> {
                article.source
            }

            !article.publishedAt.isNullOrBlank() -> {
                article.publishedAt
            }

            else -> ""
        }

        if (detailStr.isNotEmpty()) {
            Text(
                text = detailStr,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}