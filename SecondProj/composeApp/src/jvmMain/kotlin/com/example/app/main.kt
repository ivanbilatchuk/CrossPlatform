package com.example.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.app.ui.root.AppScaffold
import com.example.app.di.initKoin

fun main() = application {
    initKoin { printLogger() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Organise",
    ) {
        AppScaffold()
    }
}