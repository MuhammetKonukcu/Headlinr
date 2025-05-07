package com.muhammetkonukcu.headlinr.util

import platform.Foundation.*

actual fun getCurrentLanguage(): String {
    val locale = NSLocale.currentLocale
    val lang = locale.objectForKey(NSLocaleLanguageCode) as? String ?: "en"
    val country = locale.objectForKey(NSLocaleCountryCode) as? String ?: "US"
    return "$lang-$country"
}

actual fun getCurrentCountry(): String {
    val locale = NSLocale.currentLocale
    val country = locale.objectForKey(NSLocaleCountryCode) as? String ?: "US"
    return country.uppercase()
}

actual fun formatToLocalDate(isoString: String): String {
    val isoFormatter = NSDateFormatter().apply {
        dateFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        locale = NSLocale.localeWithLocaleIdentifier("en_US_POSIX")
        timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")
            ?: NSTimeZone.timeZoneWithName("UTC")!!
    }
    val date = isoFormatter.dateFromString(isoString) ?: return isoString

    val displayFormatter = NSDateFormatter().apply {
        dateFormat = "d MMMM yyyy"
        locale = NSLocale.localeWithLocaleIdentifier("tr_TR")
    }
    return displayFormatter.stringFromDate(date)
}

actual fun getCurrentTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}