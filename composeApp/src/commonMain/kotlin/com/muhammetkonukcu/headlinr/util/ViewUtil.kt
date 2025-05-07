package com.muhammetkonukcu.headlinr.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.check_internet_connection
import headlinr.composeapp.generated.resources.mi_refresh
import headlinr.composeapp.generated.resources.refresh
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadAsyncImage(
    modifier: Modifier,
    model: Any?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: Painter,
    errorRes: Painter,
) {
    AsyncImage(
        modifier = modifier,
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeholderRes,
        error = errorRes,
    )
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colorScheme.onTertiary,
            strokeWidth = 2.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun ErrorItem(
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.check_internet_connection),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(
            onClick = onRetryClick
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(Res.drawable.mi_refresh),
                contentDescription = stringResource(Res.string.refresh),
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}