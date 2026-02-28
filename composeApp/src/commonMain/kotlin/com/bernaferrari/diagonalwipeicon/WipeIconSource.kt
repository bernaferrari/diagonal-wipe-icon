package com.bernaferrari.diagonalwipeicon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

sealed interface WipeIconSource {
    @Composable
    fun painter(): Painter
}

@Immutable
data class DrawableWipeIcon(
    val drawable: DrawableResource,
) : WipeIconSource {
    @Composable
    override fun painter(): Painter = painterResource(drawable)
}
