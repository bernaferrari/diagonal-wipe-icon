package com.bernaferrari

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 * Defaults for [DiagonalWipeIcon].
 */
@Immutable
object DiagonalWipeIconDefaults {
    const val WipeInDurationMillis: Int = 530
    const val WipeOutDurationMillis: Int = 800

    val WipeInEasing: Easing = CubicBezierEasing(0.22f, 1f, 0.36f, 1f)
    val WipeOutEasing: Easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

    const val SeamOverlapPx: Float = 0.8f
}

enum class WipeDirection {
    TopLeftToBottomRight,
    BottomRightToTopLeft,
    TopRightToBottomLeft,
    BottomLeftToTopRight,
    TopToBottom,
    BottomToTop,
    LeftToRight,
    RightToLeft,
}

/**
 * Two-layer icon morph using a diagonal (or axis-aligned) wipe boundary.
 *
 * @param isWiped Target state: true = shows [wipedIcon]; false = shows [baseIcon].
 * @param baseIcon Icon shown when [isWiped] is false.
 * @param wipedIcon Icon shown when [isWiped] is true.
 * @param baseTint Tint for [baseIcon].
 * @param wipedTint Tint for [wipedIcon].
 * @param contentDescription Optional semantics description.
 * @param modifier Standard modifier.
 * @param wipeInDurationMillis Duration for base -> wiped transition.
 * @param wipeOutDurationMillis Duration for wiped -> base transition.
 * @param wipeInEasing Easing for base -> wiped transition.
 * @param wipeOutEasing Easing for wiped -> base transition.
 * @param seamOverlapPx Tiny overlap to prevent hairline seams.
 * @param direction Wipe movement direction.
 */
@Composable
fun DiagonalWipeIcon(
    isWiped: Boolean,
    baseIcon: ImageVector,
    wipedIcon: ImageVector,
    baseTint: Color,
    wipedTint: Color,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    wipeInDurationMillis: Int = DiagonalWipeIconDefaults.WipeInDurationMillis,
    wipeOutDurationMillis: Int = DiagonalWipeIconDefaults.WipeOutDurationMillis,
    wipeInEasing: Easing = DiagonalWipeIconDefaults.WipeInEasing,
    wipeOutEasing: Easing = DiagonalWipeIconDefaults.WipeOutEasing,
    seamOverlapPx: Float = DiagonalWipeIconDefaults.SeamOverlapPx,
    direction: WipeDirection = WipeDirection.TopLeftToBottomRight,
) {
    DiagonalWipeIcon(
        isWiped = isWiped,
        basePainter = rememberVectorPainter(baseIcon),
        wipedPainter = rememberVectorPainter(wipedIcon),
        baseTint = baseTint,
        wipedTint = wipedTint,
        contentDescription = contentDescription,
        modifier = modifier,
        wipeInDurationMillis = wipeInDurationMillis,
        wipeOutDurationMillis = wipeOutDurationMillis,
        wipeInEasing = wipeInEasing,
        wipeOutEasing = wipeOutEasing,
        seamOverlapPx = seamOverlapPx,
        direction = direction,
    )
}

/**
 * Painter variant of [DiagonalWipeIcon] for maximum flexibility.
 * Accepts any [Painter] - icons, images, custom drawings, etc.
 */
@Composable
fun DiagonalWipeIcon(
    isWiped: Boolean,
    basePainter: Painter,
    wipedPainter: Painter,
    baseTint: Color,
    wipedTint: Color,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    wipeInDurationMillis: Int = DiagonalWipeIconDefaults.WipeInDurationMillis,
    wipeOutDurationMillis: Int = DiagonalWipeIconDefaults.WipeOutDurationMillis,
    wipeInEasing: Easing = DiagonalWipeIconDefaults.WipeInEasing,
    wipeOutEasing: Easing = DiagonalWipeIconDefaults.WipeOutEasing,
    seamOverlapPx: Float = DiagonalWipeIconDefaults.SeamOverlapPx,
    direction: WipeDirection = WipeDirection.TopLeftToBottomRight,
) {
    val transition = updateTransition(targetState = isWiped, label = "diagonalWipeIcon")

    val wipeProgress by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(durationMillis = wipeInDurationMillis, easing = wipeInEasing)
            } else {
                tween(durationMillis = wipeOutDurationMillis, easing = wipeOutEasing)
            }
        },
        label = "diagonalWipeReveal",
    ) { isWipedState ->
        if (isWipedState) 1f else 0f
    }

    val progress = wipeProgress.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .semantics {
                if (contentDescription != null) {
                    this.contentDescription = contentDescription
                }
            },
    ) {
        // Fast path: fully base state
        if (progress <= 0.001f) {
            androidx.compose.foundation.Image(
                painter = basePainter,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(baseTint),
            )
            return@Box
        }

        // Fast path: fully wiped state
        if (progress >= 0.999f) {
            androidx.compose.foundation.Image(
                painter = wipedPainter,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(wipedTint),
            )
            return@Box
        }

        // Animated wipe
        Canvas(modifier = Modifier.fillMaxSize()) {
            val travelDistance = wipeTravelDistance(
                width = size.width,
                height = size.height,
                direction = direction,
            ).coerceAtLeast(1f)
            val adjustedProgress = (
                    (progress * travelDistance + seamOverlapPx) / travelDistance
                    ).coerceIn(0f, 1f)

            val revealPath = buildWipeRevealPath(
                width = size.width,
                height = size.height,
                progress = adjustedProgress,
                direction = direction,
            )

            clipPath(path = revealPath, clipOp = ClipOp.Difference) {
                with(basePainter) {
                    draw(size = size, colorFilter = ColorFilter.tint(baseTint))
                }
            }
            clipPath(path = revealPath, clipOp = ClipOp.Intersect) {
                with(wipedPainter) {
                    draw(size = size, colorFilter = ColorFilter.tint(wipedTint))
                }
            }
        }
    }
}

internal fun buildWipeBoundaryLine(
    width: Float,
    height: Float,
    progress: Float,
    direction: WipeDirection,
): Pair<Offset, Offset>? {
    val p = progress.coerceIn(0f, 1f)
    if (p <= 0f || p >= 1f) return null

    val axis = wipeAxis(direction)
    val threshold = wipeBoundaryThreshold(width, height, p, axis)
    val points = mutableListOf<Offset>()
    val eps = 0.0001f

    fun addIfInBounds(point: Offset) {
        val inBounds = point.x >= -eps && point.x <= width + eps &&
                point.y >= -eps && point.y <= height + eps
        if (!inBounds) return
        if (points.none { (it - point).getDistance() < 0.01f }) {
            points += point
        }
    }

    if (kotlin.math.abs(axis.y) > eps) {
        addIfInBounds(Offset(0f, threshold / axis.y))
        addIfInBounds(Offset(width, (threshold - axis.x * width) / axis.y))
    }
    if (kotlin.math.abs(axis.x) > eps) {
        addIfInBounds(Offset(threshold / axis.x, 0f))
        addIfInBounds(Offset((threshold - axis.y * height) / axis.x, height))
    }

    if (points.size < 2) return null
    if (points.size == 2) return points[0] to points[1]

    var bestPair = points[0] to points[1]
    var bestDistance = -1f
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            val distance = (points[i] - points[j]).getDistanceSquared()
            if (distance > bestDistance) {
                bestDistance = distance
                bestPair = points[i] to points[j]
            }
        }
    }
    return bestPair
}

internal fun buildWipeRevealPath(
    width: Float,
    height: Float,
    progress: Float,
    direction: WipeDirection,
): Path {
    val p = progress.coerceIn(0f, 1f)
    return Path().apply {
        if (p <= 0f) return@apply
        if (p >= 1f) {
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
            return@apply
        }

        val axis = wipeAxis(direction)
        val threshold = wipeBoundaryThreshold(width, height, p, axis)
        val polygon = clipRectWithHalfPlane(width, height, axis, threshold)
        if (polygon.isEmpty()) return@apply
        moveTo(polygon.first().x, polygon.first().y)
        for (i in 1 until polygon.size) {
            lineTo(polygon[i].x, polygon[i].y)
        }
        close()
    }
}

internal fun wipeTravelDistance(
    width: Float,
    height: Float,
    direction: WipeDirection,
): Float {
    val axis = wipeAxis(direction)
    val corners = rectangleCorners(width, height)
    val minValue = corners.minOf { dot(axis, it) }
    val maxValue = corners.maxOf { dot(axis, it) }
    return (maxValue - minValue).coerceAtLeast(1f)
}

private fun wipeAxis(direction: WipeDirection): Offset {
    return when (direction) {
        WipeDirection.TopLeftToBottomRight -> Offset(1f, 1f)
        WipeDirection.BottomRightToTopLeft -> Offset(-1f, -1f)
        WipeDirection.TopRightToBottomLeft -> Offset(-1f, 1f)
        WipeDirection.BottomLeftToTopRight -> Offset(1f, -1f)
        WipeDirection.TopToBottom -> Offset(0f, 1f)
        WipeDirection.BottomToTop -> Offset(0f, -1f)
        WipeDirection.LeftToRight -> Offset(1f, 0f)
        WipeDirection.RightToLeft -> Offset(-1f, 0f)
    }
}

private fun wipeBoundaryThreshold(
    width: Float,
    height: Float,
    progress: Float,
    axis: Offset,
): Float {
    val corners = rectangleCorners(width, height)
    val minValue = corners.minOf { dot(axis, it) }
    val maxValue = corners.maxOf { dot(axis, it) }
    return minValue + (maxValue - minValue) * progress.coerceIn(0f, 1f)
}

private fun clipRectWithHalfPlane(
    width: Float,
    height: Float,
    axis: Offset,
    threshold: Float,
): List<Offset> {
    val rectangle = rectangleCorners(width, height)
    return clipPolygonWithHalfPlane(rectangle, axis, threshold)
}

private fun rectangleCorners(width: Float, height: Float): List<Offset> = listOf(
    Offset(0f, 0f),
    Offset(width, 0f),
    Offset(width, height),
    Offset(0f, height),
)

private fun clipPolygonWithHalfPlane(
    polygon: List<Offset>,
    axis: Offset,
    threshold: Float,
): List<Offset> {
    if (polygon.isEmpty()) return emptyList()
    val output = mutableListOf<Offset>()
    val eps = 0.0001f

    fun inside(point: Offset): Boolean {
        return dot(axis, point) <= threshold + eps
    }

    var previous = polygon.last()
    var previousInside = inside(previous)
    for (current in polygon) {
        val currentInside = inside(current)
        when {
            previousInside && currentInside -> output += current
            previousInside && !currentInside -> {
                intersectSegmentWithBoundary(previous, current, axis, threshold)?.let(output::add)
            }
            !previousInside && currentInside -> {
                intersectSegmentWithBoundary(previous, current, axis, threshold)?.let(output::add)
                output += current
            }
        }
        previous = current
        previousInside = currentInside
    }
    return output
}

private fun intersectSegmentWithBoundary(
    start: Offset,
    end: Offset,
    axis: Offset,
    threshold: Float,
): Offset? {
    val startValue = dot(axis, start) - threshold
    val endValue = dot(axis, end) - threshold
    val denominator = startValue - endValue
    if (kotlin.math.abs(denominator) < 0.0001f) return null
    val t = (startValue / denominator).coerceIn(0f, 1f)
    return Offset(
        x = start.x + (end.x - start.x) * t,
        y = start.y + (end.y - start.y) * t,
    )
}

private fun dot(axis: Offset, point: Offset): Float = axis.x * point.x + axis.y * point.y
