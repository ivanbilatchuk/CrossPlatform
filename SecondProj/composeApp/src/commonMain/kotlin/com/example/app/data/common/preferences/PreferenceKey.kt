package com.example.app.data.common.preferences

enum class PreferenceKey {
    ABOUT_VISITED_COUNT,
    ABOUT_VISITED_DATE;

    val key get() = this.name
}