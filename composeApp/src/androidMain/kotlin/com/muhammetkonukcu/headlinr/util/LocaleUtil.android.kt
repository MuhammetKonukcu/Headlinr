package com.muhammetkonukcu.headlinr.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

actual fun getCurrentLanguage(): String = Locale.getDefault().toLanguageTag()

actual fun getCurrentCountry(): String {
    return Locale.getDefault().country.lowercase().ifEmpty { "us" }
}

actual fun formatToLocalDate(isoString: String): String {
    val odt = OffsetDateTime.parse(isoString)
    val formatter = DateTimeFormatter
        .ofPattern("d MMMM yyyy", Locale(getCurrentCountry()))
    return odt.format(formatter)
}

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()