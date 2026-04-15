package com.example.app.datetime
import co.touchlab.kermit.Logger
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateTimeService : IDateTimeService {

    private val logger = Logger.withTag("DateTimeService")

    override fun getCurrentTime(): String {
        return try {
            val currentInstant: Instant = Clock.System.now()
            val localDateTime: LocalDateTime = currentInstant
                .toLocalDateTime(TimeZone.currentSystemDefault())
            val formattedTime = formatTime(localDateTime)
            logger.d("getCurrentTime: $formattedTime")
            formattedTime
        } catch (e: Exception) {
            logger.e("Error getting current time", e)
            "N/A"
        }
    }

    override fun getCurrentDate(): String {
        return try {
            val currentInstant: Instant = Clock.System.now()
            val localDateTime: LocalDateTime = currentInstant
                .toLocalDateTime(TimeZone.currentSystemDefault())
            val formattedDate = formatDate(localDateTime)
            logger.d("getCurrentDate: $formattedDate")
            formattedDate
        } catch (e: Exception) {
            logger.e("Error getting current date", e)
            "N/A"
        }
    }

    override fun getCurrentTimeZone(): String {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val zoneId = timeZone.toString()
            logger.d("getCurrentTimeZone: $zoneId")
            zoneId
        } catch (e: Exception) {
            logger.e("Error getting current timezone", e)
            "N/A"
        }
    }

    override fun getTimeInTimeZone(timeZoneId: String): String {
        return try {
            val timeZone = TimeZone.of(timeZoneId)
            val currentInstant: Instant = Clock.System.now()
            val localDateTime: LocalDateTime = currentInstant.toLocalDateTime(timeZone)
            val formattedTime = formatTime(localDateTime)
            logger.d("getTimeInTimeZone($timeZoneId): $formattedTime")
            formattedTime
        } catch (e: Exception) {
            logger.e("Error getting time in timezone: $timeZoneId", e)
            "N/A"
        }
    }

    override fun getDateInTimeZone(timeZoneId: String): String {
        return try {
            val timeZone = TimeZone.of(timeZoneId)
            val currentInstant: Instant = Clock.System.now()
            val localDateTime: LocalDateTime = currentInstant.toLocalDateTime(timeZone)
            val formattedDate = formatDate(localDateTime)
            logger.d("getDateInTimeZone($timeZoneId): $formattedDate")
            formattedDate
        } catch (e: Exception) {
            logger.e("Error getting date in timezone: $timeZoneId", e)
            "N/A"
        }
    }

    override fun getAvailableTimeZones(): List<String> {
        return try {
            val timeZones = TimeZone.availableZoneIds.sorted()
            logger.d("getAvailableTimeZones: found ${timeZones.size} zones")
            timeZones
        } catch (e: Exception) {
            logger.e("Error getting available timezones", e)
            emptyList()
        }
    }

    override fun getTimeZoneOffset(timeZoneId: String): String {
        return try {
            val currentTimeZone = TimeZone.currentSystemDefault()
            val otherTimeZone = TimeZone.of(timeZoneId)
            val currentInstant: Instant = Clock.System.now()

            val currentDateTime: LocalDateTime = currentInstant.toLocalDateTime(currentTimeZone)
            val otherDateTime: LocalDateTime = currentInstant.toLocalDateTime(otherTimeZone)

            val hourOffset = otherDateTime.hour - currentDateTime.hour
            val offset = if (hourOffset >= 0) "+$hourOffset" else "$hourOffset"

            logger.d("getTimeZoneOffset($timeZoneId): $offset")
            offset
        } catch (e: Exception) {
            logger.e("Error calculating timezone offset: $timeZoneId", e)
            "N/A"
        }
    }

    private fun formatTime(dateTime: LocalDateTime): String {
        var hour = dateTime.hour % 12
        if (hour == 0) hour = 12
        val minute = dateTime.minute.toString().padStart(2, '0')
        val second = dateTime.second.toString().padStart(2, '0')
        val amPm = if (dateTime.hour < 12) "AM" else "PM"
        return "$hour:$minute:$second $amPm"
    }

    private fun formatDate(dateTime: LocalDateTime): String {
        val dayOfWeek = dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
        val month = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val day = dateTime.dayOfMonth
        val year = dateTime.year
        return "$dayOfWeek, $month $day, $year"
    }
}