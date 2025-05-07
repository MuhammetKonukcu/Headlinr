package com.muhammetkonukcu.headlinr.util

expect fun getCurrentLanguage(): String

expect fun getCurrentCountry(): String

expect fun formatToLocalDate(isoString: String): String

expect fun getCurrentTimeMillis(): Long