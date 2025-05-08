package com.muhammetkonukcu.headlinr.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class FlagModel(
    val countryCode: String,
    val imageRes: DrawableResource,
    val countryName: StringResource,
    var isSelected: Boolean = false
)
