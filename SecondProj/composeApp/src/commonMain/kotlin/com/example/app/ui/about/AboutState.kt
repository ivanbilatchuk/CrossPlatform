package com.example.app.ui.about

import kotlinx.datetime.LocalDateTime

internal data class AboutState(
    val platformInfo: List<Pair<String, String>> = emptyList(),
    val visitedCount: Int = 0,
    val visitedDate: String = ""
)