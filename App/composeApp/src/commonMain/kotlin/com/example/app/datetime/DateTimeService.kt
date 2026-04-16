package com.example.app.datetime

import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class DateTimeService : IDateTimeService {

    override fun getAvailableTimeZones(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    override fun getCurrentTime(): String {
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment
            .toLocalDateTime(TimeZone.currentSystemDefault())
        Napier.d("getCurrentTime: ${formatDateTime(dateTime)}", tag = "DateTimeService")
        return formatDateTime(dateTime)
    }

    override fun getCurrentDate(): String {
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment
            .toLocalDateTime(TimeZone.currentSystemDefault())
        Napier.d("getCurrentDate: ${formatDate(dateTime)}", tag = "DateTimeService")
        return formatDate(dateTime)
    }

    override fun getCurrentTimeZone(): String {
        val currentTimeZone = TimeZone.currentSystemDefault()
        Napier.d("getCurrentTimeZone: $currentTimeZone", tag = "DateTimeService")
        return currentTimeZone.toString()
    }

    override fun getTimeInTimeZone(timeZoneId: String): String {
        val timezone = TimeZone.of(timeZoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        return formatDateTime(dateTime)
    }

    override fun getDateInTimeZone(timeZoneId: String): String {
        val timezone = TimeZone.of(timeZoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        return formatDate(dateTime)
    }

    override fun getTimeZoneOffset(timeZoneId: String): String {
        val now = Clock.System.now()
        val currentTimeZone = TimeZone.currentSystemDefault()
        val targetTimeZone = TimeZone.of(timeZoneId)

        val currentOffsetSeconds = currentTimeZone.offsetAt(now).totalSeconds
        val targetOffsetSeconds = targetTimeZone.offsetAt(now).totalSeconds

        val diffHours = (targetOffsetSeconds - currentOffsetSeconds) / 3600
        Napier.d("getTimeZoneOffset for $timeZoneId: ${diffHours}h", tag = "DateTimeService")
        return if (diffHours >= 0) "+${diffHours}h" else "${diffHours}h"
    }

    private fun formatDateTime(dateTime: LocalDateTime): String {
        val minute = dateTime.minute
        var hour = dateTime.hour % 12
        if (hour == 0) hour = 12
        val amPm = if (dateTime.hour < 12) "AM" else "PM"
        return "$hour:${minute.toString().padStart(2, '0')} $amPm"
    }

    private fun formatDate(dateTime: LocalDateTime): String {
        return "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, " +
                "${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.dayOfMonth}, ${dateTime.year}"
    }
}