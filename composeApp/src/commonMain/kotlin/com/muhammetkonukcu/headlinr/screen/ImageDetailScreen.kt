package com.muhammetkonukcu.headlinr.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.muhammetkonukcu.headlinr.theme.Black
import com.muhammetkonukcu.headlinr.theme.Neutral200
import com.muhammetkonukcu.headlinr.theme.White
import com.muhammetkonukcu.headlinr.zoom.Settings
import com.muhammetkonukcu.headlinr.zoom.pinchToZoomSnapBack
import com.muhammetkonukcu.headlinr.zoom.rememberZoomState
import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.back
import headlinr.composeapp.generated.resources.ph_caret_left
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageDetailScreen(
    navController: NavController,
    imageUrl: String
) {
    Scaffold(
        modifier = Modifier.background(color = Black),
        topBar = { DetailTopBar(navController = navController) }) {
        var settings by remember { mutableStateOf(Settings()) }

        val painter =
            rememberAsyncImagePainter(model = imageUrl)
        val state = painter.state

        val contentSize = (state as? AsyncImagePainter.State.Success)
            ?.painter
            ?.intrinsicSize
            ?: Size.Zero

        val zoomState = rememberZoomState(
            contentSize = contentSize,
            initialScale = settings.initialScale
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .pinchToZoomSnapBack(
                        zoomState = zoomState,
                        zoomEnabled = settings.zoomEnabled,
                    )
            )

            if (state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Neutral200,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}

@Composable
private fun DetailTopBar(
    navController: NavController,
) {
    Row(
        modifier = Modifier.fillMaxWidth().statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.navigateUp() }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.ph_caret_left),
                contentDescription = stringResource(Res.string.back),
                tint = White
            )
        }
    }
}