package com.example.app.data.about

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import com.example.app.data.common.preferences.Preferences
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Clock
import kotlin.time.Instant

internal class AboutRepository(
    private val platform: Platform,
    private val preferences: Preferences
) {
    fun getAbout(): MutableList<Pair<String, String>> {
        val items = mutableListOf(
            Pair("Operating System", "${platform.osName} ${platform.osVersion}"),
            Pair("Device", platform.deviceModel),
            Pair("CPU", platform.cpuType)
        )
        val max = max(platform.screen.width, platform.screen.height)
        val min = min(platform.screen.width, platform.screen.height)
        var displayInfo = "${max}×${min}"
        platform.screen.density?.let {
            displayInfo += " ${it}x"
        }
        items.add(Pair("Display", displayInfo))
        return items
    }

    fun increaseVisitCount() {
        preferences.aboutVisitedCount++
    }

    fun visitedCount(): Int {
        return preferences.aboutVisitedCount
    }

    fun visitedCountObservable(): Flow<Int> {
        return preferences.observableAboutVisitedCount
    }

    fun updateVisitedDate() {
        val now: Instant = Clock.System.now()
        val localNow: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        preferences.aboutVisitedDate = localNow
    }

    fun visitedDate(): LocalDateTime? {
        return preferences.aboutVisitedDate
    }
}