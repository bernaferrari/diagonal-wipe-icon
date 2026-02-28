package com.bernaferrari.diagonalwipeicon

class JsPlatform : Platform {
    override val name: String = "Web"
}

actual fun getPlatform(): Platform = JsPlatform()
