package com.example.app

import com.example.app.data.about.Platform

class Greeting {
    private val platform = Platform()

    fun greet(): String {
        return "Hello, ${platform.osName}!"
    }
}