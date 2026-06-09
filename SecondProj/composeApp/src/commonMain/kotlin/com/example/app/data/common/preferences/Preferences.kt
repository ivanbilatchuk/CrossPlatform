package com.example.app.data.common.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface Preferences {

    var aboutVisitedCount: Int

    var aboutVisitedDate: LocalDateTime?

    val observableAboutVisitedCount: Flow<Int>

    val observableAboutVisitedDate: Flow<LocalDateTime>

    fun cleanStorage()
}