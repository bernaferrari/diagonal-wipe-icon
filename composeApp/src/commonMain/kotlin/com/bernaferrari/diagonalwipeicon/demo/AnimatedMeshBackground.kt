package com.bernaferrari.diagonalwipeicon.demo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun AnimatedMeshBackground(seedColor: Color, isDark: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "mesh")

    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(35000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mesh1"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF0D0D0D) else Color(0xFFFAFAFA))
            .drawBehind {
                val centerX = size.width / 2
                val centerY = size.height / 3

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            seedColor.copy(alpha = 0.04f),
                            Color.Transparent
                        ),
                        center = Offset(
                            centerX + cos(offset1) * 100f,
                            centerY + sin(offset1) * 50f
                        ),
                        radius = 500f
                    ),
                    radius = 500f,
                    center = Offset(
                        centerX + cos(offset1) * 100f,
                        centerY + sin(offset1) * 50f
                    )
                )
            }
    )
}
