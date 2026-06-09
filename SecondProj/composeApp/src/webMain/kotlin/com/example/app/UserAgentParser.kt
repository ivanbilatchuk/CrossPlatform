package com.example.app

fun parseBrowserName(ua: String): String = when {
    ua.contains("Firefox", ignoreCase = true)  -> "Firefox"
    ua.contains("Edg/", ignoreCase = true)     -> "Edge"
    ua.contains("OPR/", ignoreCase = true) ||
            ua.contains("Opera", ignoreCase = true)    -> "Opera"
    ua.contains("Chrome", ignoreCase = true)   -> "Chrome"
    ua.contains("Safari", ignoreCase = true)   -> "Safari"
    else -> "Unknown"
}

fun parseBrowserVersion(ua: String): String {
    val patterns = mapOf(
        "Firefox" to Regex("Firefox/([\\d.]+)"),
        "Edg" to Regex("Edg/([\\d.]+)"),
        "OPR" to Regex("OPR/([\\d.]+)"),
        "Chrome" to Regex("Chrome/([\\d.]+)"),
        "Safari" to Regex("Version/([\\d.]+)")
    )
    for ((_, regex) in patterns) {
        val match = regex.find(ua)
        if (match != null) return match.groupValues[1]
    }
    return "Unknown"
}

fun parseOsVersion(ua: String): String {
    val patterns = listOf(
        Regex("Windows NT ([\\d.]+)"),          // Windows
        Regex("Android ([\\d.]+)"),             // Android
        Regex("OS ([\\d_]+) like Mac OS X"),    // iOS  (e.g. OS 17_0)
        Regex("Mac OS X ([\\d_.]+)"),           // macOS
        Regex("CrOS [\\w]+ ([\\d.]+)")          // ChromeOS
    )
    return patterns.firstNotNullOfOrNull { regex ->
        regex.find(ua)?.groupValues?.get(1)
            ?.replace('_', '.')                 // iOS uses underscores
    } ?: "Unknown"
}

fun parseOsName(ua: String): String = when {
    ua.contains("Windows", ignoreCase = true) -> "Windows"
    ua.contains("Android", ignoreCase = true) -> "Android"
    ua.contains("iPhone", ignoreCase = true) ||
            ua.contains("iPad", ignoreCase = true) -> "iOS"
    ua.contains("Mac OS X", ignoreCase = true) -> "macOS"
    ua.contains("Linux", ignoreCase = true) -> "Linux"
    ua.contains("CrOS", ignoreCase = true) -> "ChromeOS"
    else -> "Unknown"
}


fun parseLayoutEngine(ua: String): String = when {
    ua.contains("Gecko/", ignoreCase = true) &&
            !ua.contains("like Gecko", ignoreCase = true) -> "Gecko"
    ua.contains("AppleWebKit", ignoreCase = true) -> "WebKit / Blink"
    ua.contains("Presto", ignoreCase = true) -> "Presto"
    ua.contains("Trident", ignoreCase = true) -> "Trident"
    else -> "Unknown"
}