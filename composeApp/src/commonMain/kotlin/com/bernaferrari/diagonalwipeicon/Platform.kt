package com.bernaferrari.diagonalwipeicon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform