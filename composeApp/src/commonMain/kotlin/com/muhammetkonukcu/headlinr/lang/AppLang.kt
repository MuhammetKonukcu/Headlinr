package com.muhammetkonukcu.headlinr.lang

import headlinr.composeapp.generated.resources.Res
import headlinr.composeapp.generated.resources.en
import headlinr.composeapp.generated.resources.tr
import org.jetbrains.compose.resources.StringResource

enum class AppLang(
    val code: String,
    val stringRes: StringResource
) {
    English("en", Res.string.en),
    Turkish("tr", Res.string.tr)
}