package com.bernaferrari

class JsPlatform : Platform {
    override val name: String = "Web"
}

actual fun getPlatform(): Platform = JsPlatform()
