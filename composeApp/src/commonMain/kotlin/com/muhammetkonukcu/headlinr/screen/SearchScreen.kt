package com.muhammetkonukcu.headlinr.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.muhammetkonukcu.headlinr.remote.entity.Article
import com.muhammetkonukcu.headlinr.theme.Blue500
import com.muhammetkonukcu.headlinr.theme.White
import com.muhammetkonukcu.headlinr.util.ErrorItem
import com.muhammetkonukcu.headlinr.util.LoadingItem
import com.muhammetkonukcu.headlinr.viewmodel.SearchViewModel
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.ph_magnifying_glass
import headlinr.composeapp.generated.resources.search
import headlinr.composeapp.generated.resources.search_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SearchScreen(navController: NavController, innerPadding: PaddingValues) {
    val viewModel = koinViewModel<SearchViewModel>()
    val lazyPagingItems = viewModel.searchResult.collectAsLazyPagingItems()
    var keyword by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = innerPadding.calculateBottomPadding(),
                top = innerPadding.calculateTopPadding()
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier) {
            SearchBar(
                viewModel = viewModel,
                userInput = keyword,
                onTextChange = { newValue ->
                    keyword = newValue
                }
            )

            ArticlesLazyColumn(lazyPagingItems = lazyPagingItems, searchViewModel = viewModel)
        }
    }
}

@Composable
private fun SearchBar(
    viewModel: SearchViewModel,
    userInput: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var isSearchEnabled by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max)
                    .border(
                        width = 1.dp,
                        color = if (isSearchEnabled) Blue500 else Blue500.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            bottomStart = 12.dp
                        )
                    ),
                value = userInput,
                onValueChange = { newValue ->
                    onTextChange.invoke(newValue)
                    viewModel.updateKeyword(newValue.trim())
                    isSearchEnabled = newValue.isNotEmpty()
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                placeholder = {
                    val placeholderStr = stringResource(Res.string.search_hint)
                    Text(
                        text = placeholderStr,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
            )

            FilledIconButton(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .width(IntrinsicSize.Max)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Blue500,
                    contentColor = Blue500,
                    disabledContentColor = Blue500.copy(alpha = 0.5f),
                    disabledContainerColor = Blue500.copy(alpha = 0.5f)
                ),
                enabled = isSearchEnabled,
                onClick = {},
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ph_magnifying_glass),
                    contentDescription = stringResource(Res.string.search),
                    tint = White
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            thickness = 1.dp
        )
    }
}

@Composable
fun ArticlesLazyColumn(
    lazyPagingItems: LazyPagingItems<Article>,
    searchViewModel: SearchViewModel
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
                ArticleItem(article = item, onFavClicked = { value ->
                    if (value)
                        searchViewModel.addFavoriteNews(item)
                    else
                        searchViewModel.removeFavoriteNews(item.url)
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
