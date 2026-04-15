package com.example.app.datetime

interface IDateTimeService {
    fun getCurrentTime(): String
    fun getCurrentDate(): String
    fun getCurrentTimeZone(): String
    fun getTimeInTimeZone(timeZoneId: String): String
    fun getDateInTimeZone(timeZoneId: String): String
    fun getAvailableTimeZones(): List<String>
    fun getTimeZoneOffset(timeZoneId: String): String
}