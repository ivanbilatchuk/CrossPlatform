package com.example.lab2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform