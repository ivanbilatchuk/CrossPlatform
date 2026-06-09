package com.example.app.data.about

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class Platform() {
    val osName: String
    val osVersion: String

    val deviceModel: String
    val cpuType: String

    val screen: ScreenInfo

    fun logSystemInfo()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ScreenInfo() {
    val width: Int
    val height: Int
    val density: Int?
}

val Platform.deviceInfo: String
    get() {
        var result = "($osName; $osVersion; $deviceModel; ${screen.width}x${screen.height}"

        screen.density?.let {
            result += "@${it}x; "
        }

        result += "$cpuType)"
        return result
    }