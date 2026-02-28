package com.bernaferrari.diagonalwipeicon

import kotlin.math.roundToInt

internal const val SlowAnimationMultiplier = 2.4f
internal const val AutoPlayTurnaroundMillis = 180
internal const val AutoPlaySlowTurnaroundMillis = 90

internal fun scaledDuration(duration: Int, multiplier: Float): Int {
    return (duration * multiplier).roundToInt().coerceAtLeast(1)
}

internal fun autoPlayDelay(duration: Int, multiplier: Float): Int {
    val turnaround = if (multiplier > 1f) {
        AutoPlaySlowTurnaroundMillis
    } else {
        AutoPlayTurnaroundMillis
    }
    return scaledDuration(duration, multiplier) + turnaround
}
