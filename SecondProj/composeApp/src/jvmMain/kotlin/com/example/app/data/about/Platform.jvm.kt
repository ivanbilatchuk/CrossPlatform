package com.example.app.data.about

import co.touchlab.kermit.Logger
import java.awt.Toolkit

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class Platform actual constructor() {
    actual val osName: String
        get() = System.getProperty("os.name") ?: "Desktop"
    actual val osVersion: String
        get() = System.getProperty("os.version") ?: "---"
    actual val deviceModel: String
        get() = "Desktop"
    actual val cpuType: String
        get() = System.getProperty("os.arch") ?: "---"

    actual val screen: ScreenInfo
        get() = ScreenInfo()

    actual fun logSystemInfo() {
        Logger.d(deviceInfo)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class ScreenInfo actual constructor() {

    private val toolkit = Toolkit.getDefaultToolkit()

    actual val width: Int
        get() = toolkit.screenSize.width
    actual val height: Int
        get() = toolkit.screenSize.height
    actual val density: Int?
        get() = null
}