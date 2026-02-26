package com.bernaferrari

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform