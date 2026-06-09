package com.example.app.data.about

import co.touchlab.kermit.Logger
import kotlinx.browser.window
import com.example.app.parseBrowserName
import com.example.app.parseBrowserVersion
import com.example.app.parseLayoutEngine
import com.example.app.parseOsName
import com.example.app.parseOsVersion

actual open class Platform actual constructor() {
    actual val osName: String
        get() = parseBrowserName(window.navigator.userAgent)
    actual val osVersion: String
        get() = parseBrowserVersion(window.navigator.userAgent)
    actual val deviceModel: String
        get() = parseOsName(window.navigator.userAgent) + " " + parseOsVersion(window.navigator.userAgent)
    actual val cpuType: String
        get() = parseLayoutEngine(window.navigator.userAgent)
    actual val screen: ScreenInfo
        get() = ScreenInfo()

    actual fun logSystemInfo() {
        Logger.d("Platform $deviceInfo")
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ScreenInfo actual constructor() {
    val pageWidth = window.innerWidth
    val pageHeight = window.innerHeight
    val deviceDensity = window.devicePixelRatio

    actual val width: Int
        get() = pageWidth
    actual val height: Int
        get() = pageHeight
    actual val density: Int?
        get() = deviceDensity.toInt()
}