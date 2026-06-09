package com.example.app.ui.about

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import com.example.app.data.about.AboutRepository

@Stable
internal class AboutViewModel(
    private val aboutRepository: AboutRepository,
) : ViewModel() {

    private val format: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        day()
        char('.')
        monthNumber()
        char('.')
        year()
        char(' ')
        hour()
        char(':')
        minute()
    }

    val countState: StateFlow<Int> = aboutRepository.visitedCountObservable()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()

    init {
        Logger.w("init")
        aboutRepository.increaseVisitCount()
        aboutRepository.updateVisitedDate()
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            val platformInfo = aboutRepository.getAbout()
            val visitedCount = aboutRepository.visitedCount()
            val lastVisitedDate = aboutRepository.visitedDate()?.format(format) ?: "-----"
            _state.update { current ->
                current.copy(
                    platformInfo = platformInfo,
                    visitedCount = visitedCount,
                    visitedDate = lastVisitedDate
                )
            }
        }
    }
}