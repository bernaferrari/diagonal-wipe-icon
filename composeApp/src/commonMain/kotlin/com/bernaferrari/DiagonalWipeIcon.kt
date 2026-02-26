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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.material3.Icon
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.graphics.drawscope.clipPath

/**
 * Defaults for [DiagonalWipeIcon].
 *
 * Keep these values centralized so callers can reuse the same motion language or override
 * a single knob (for example only duration) without rewriting the full component.
 */
@Immutable
object DiagonalWipeIconDefaults {
    // "Enable" here means toggling from allowed -> blocked.
    const val EnableDurationMillis: Int = 530

    // "Disable" here means toggling from blocked -> allowed.
    const val DisableDurationMillis: Int = 800

    // Snappy entry to make blocked feel responsive.
    val EnableEasing: Easing = CubicBezierEasing(0.22f, 1f, 0.36f, 1f)

    // Slightly gentler exit for a softer return to allowed.
    val DisableEasing: Easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

    // Small overlap avoids a visible seam between clipped layers on some densities.
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
 * Two-layer icon morph using one diagonal wipe boundary.
 *
 * Layer model:
 * 1) Allowed icon exits from the non-revealed side.
 * 2) Blocked icon enters from the revealed side.
 *
 * Because both layers use the same path and same progress in the same frame, they remain synced.
 *
 * @param blocked Target state: true = blocked icon fully visible; false = allowed icon fully visible.
 * @param allowedIcon Icon used when state is allowed.
 * @param blockedIcon Icon used when state is blocked.
 * @param allowedTint Tint for [allowedIcon].
 * @param blockedTint Tint for [blockedIcon].
 * @param contentDescription Optional semantics description.
 * @param modifier Standard modifier.
 * @param enableDurationMillis Duration when moving from allowed -> blocked.
 * @param disableDurationMillis Duration when moving from blocked -> allowed.
 * @param enableEasing Easing for allowed -> blocked transition.
 * @param disableEasing Easing for blocked -> allowed transition.
 * @param seamOverlapPx Tiny overlap to prevent hairline seams.
 * @param direction Wipe movement direction.
 */
@Composable
fun DiagonalWipeIcon(
    blocked: Boolean,
    allowedIcon: ImageVector,
    blockedIcon: ImageVector,
    allowedTint: Color,
    blockedTint: Color,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    enableDurationMillis: Int = DiagonalWipeIconDefaults.EnableDurationMillis,
    disableDurationMillis: Int = DiagonalWipeIconDefaults.DisableDurationMillis,
    enableEasing: Easing = DiagonalWipeIconDefaults.EnableEasing,
    disableEasing: Easing = DiagonalWipeIconDefaults.DisableEasing,
    seamOverlapPx: Float = DiagonalWipeIconDefaults.SeamOverlapPx,
    direction: WipeDirection = WipeDirection.TopLeftToBottomRight,
) {
    // Transition is keyed by the target blocked state.
    val transition = updateTransition(targetState = blocked, label = "diagonalWipeIcon")

    // Vector painters are reused across frames for efficient icon drawing in Canvas.
    val allowedPainter = rememberVectorPainter(allowedIcon)
    val blockedPainter = rememberVectorPainter(blockedIcon)

    // Shared progress for both layers. 0 = fully allowed, 1 = fully blocked.
    val blockedRevealProgress by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(durationMillis = enableDurationMillis, easing = enableEasing)
            } else {
                tween(durationMillis = disableDurationMillis, easing = disableEasing)
            }
        },
        label = "diagonalWipeReveal",
    ) { isBlocked ->
        if (isBlocked) 1f else 0f
    }

    // Clamp to protect against tiny numeric overshoots.
    val blockedProgress = blockedRevealProgress.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            // Offscreen composition ensures clipping behaves consistently across draws.
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            // Keep accessibility semantics attached to the whole icon.
            .semantics {
                if (contentDescription != null) {
                    this.contentDescription = contentDescription
                }
            },
    ) {
        // Fast path: no in-between draw work when fully allowed.
        if (blockedProgress <= 0.001f) {
            Icon(
                imageVector = allowedIcon,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                tint = allowedTint,
            )
            return@Box
        }

        // Fast path: no in-between draw work when fully blocked.
        if (blockedProgress >= 0.999f) {
            Icon(
                imageVector = blockedIcon,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                tint = blockedTint,
            )
            return@Box
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            // Convert normalized progress into directional coverage, then add a tiny overlap
            // so both clipped regions meet without a gap.
            val travelDistance = wipeTravelDistance(
                width = size.width,
                height = size.height,
                direction = direction,
            ).coerceAtLeast(1f)
            val adjustedBlockedProgress = (
                    (blockedProgress * travelDistance + seamOverlapPx) / travelDistance
                    ).coerceIn(0f, 1f)

            // This single path defines both entering and exiting regions.
            val blockedRevealPath = buildWipeRevealPath(
                width = size.width,
                height = size.height,
                progress = adjustedBlockedProgress,
                direction = direction,
            )

            // Allowed (first layer) exits while blocked (second layer) enters
            // with the exact same boundary from opposite clipped regions.
            clipPath(path = blockedRevealPath, clipOp = ClipOp.Difference) {
                with(allowedPainter) {
                    draw(size = size, colorFilter = ColorFilter.tint(allowedTint))
                }
            }
            clipPath(path = blockedRevealPath, clipOp = ClipOp.Intersect) {
                with(blockedPainter) {
                    draw(size = size, colorFilter = ColorFilter.tint(blockedTint))
                }
            }
        }
    }
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

internal fun buildWipeBoundaryLine(
    width: Float,
    height: Float,
    progress: Float,
    direction: WipeDirection,
): Pair<androidx.compose.ui.geometry.Offset, androidx.compose.ui.geometry.Offset>? {
    val p = progress.coerceIn(0f, 1f)
    if (p <= 0f || p >= 1f) return null

    val axis = wipeAxis(direction)
    val threshold = wipeBoundaryThreshold(width, height, p, axis)
    val points = mutableListOf<androidx.compose.ui.geometry.Offset>()
    val eps = 0.0001f

    fun addIfInBounds(point: androidx.compose.ui.geometry.Offset) {
        val inBounds = point.x >= -eps && point.x <= width + eps &&
                point.y >= -eps && point.y <= height + eps
        if (!inBounds) return
        if (points.none { (it - point).getDistance() < 0.01f }) {
            points += point
        }
    }

    if (kotlin.math.abs(axis.y) > eps) {
        addIfInBounds(androidx.compose.ui.geometry.Offset(0f, threshold / axis.y))
        addIfInBounds(androidx.compose.ui.geometry.Offset(width, (threshold - axis.x * width) / axis.y))
    }
    if (kotlin.math.abs(axis.x) > eps) {
        addIfInBounds(androidx.compose.ui.geometry.Offset(threshold / axis.x, 0f))
        addIfInBounds(androidx.compose.ui.geometry.Offset((threshold - axis.y * height) / axis.x, height))
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

private fun wipeAxis(direction: WipeDirection): androidx.compose.ui.geometry.Offset {
    return when (direction) {
        WipeDirection.TopLeftToBottomRight -> androidx.compose.ui.geometry.Offset(1f, 1f)
        WipeDirection.BottomRightToTopLeft -> androidx.compose.ui.geometry.Offset(-1f, -1f)
        WipeDirection.TopRightToBottomLeft -> androidx.compose.ui.geometry.Offset(-1f, 1f)
        WipeDirection.BottomLeftToTopRight -> androidx.compose.ui.geometry.Offset(1f, -1f)
        WipeDirection.TopToBottom -> androidx.compose.ui.geometry.Offset(0f, 1f)
        WipeDirection.BottomToTop -> androidx.compose.ui.geometry.Offset(0f, -1f)
        WipeDirection.LeftToRight -> androidx.compose.ui.geometry.Offset(1f, 0f)
        WipeDirection.RightToLeft -> androidx.compose.ui.geometry.Offset(-1f, 0f)
    }
}

private fun wipeBoundaryThreshold(
    width: Float,
    height: Float,
    progress: Float,
    axis: androidx.compose.ui.geometry.Offset,
): Float {
    val corners = rectangleCorners(width, height)
    val minValue = corners.minOf { dot(axis, it) }
    val maxValue = corners.maxOf { dot(axis, it) }
    return minValue + (maxValue - minValue) * progress.coerceIn(0f, 1f)
}

private fun clipRectWithHalfPlane(
    width: Float,
    height: Float,
    axis: androidx.compose.ui.geometry.Offset,
    threshold: Float,
): List<androidx.compose.ui.geometry.Offset> {
    val rectangle = rectangleCorners(width, height)
    return clipPolygonWithHalfPlane(rectangle, axis, threshold)
}

private fun rectangleCorners(
    width: Float,
    height: Float,
): List<androidx.compose.ui.geometry.Offset> = listOf(
    androidx.compose.ui.geometry.Offset(0f, 0f),
    androidx.compose.ui.geometry.Offset(width, 0f),
    androidx.compose.ui.geometry.Offset(width, height),
    androidx.compose.ui.geometry.Offset(0f, height),
)

private fun clipPolygonWithHalfPlane(
    polygon: List<androidx.compose.ui.geometry.Offset>,
    axis: androidx.compose.ui.geometry.Offset,
    threshold: Float,
): List<androidx.compose.ui.geometry.Offset> {
    if (polygon.isEmpty()) return emptyList()
    val output = mutableListOf<androidx.compose.ui.geometry.Offset>()
    val eps = 0.0001f

    fun inside(point: androidx.compose.ui.geometry.Offset): Boolean {
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
    start: androidx.compose.ui.geometry.Offset,
    end: androidx.compose.ui.geometry.Offset,
    axis: androidx.compose.ui.geometry.Offset,
    threshold: Float,
): androidx.compose.ui.geometry.Offset? {
    val startValue = dot(axis, start) - threshold
    val endValue = dot(axis, end) - threshold
    val denominator = startValue - endValue
    if (kotlin.math.abs(denominator) < 0.0001f) return null
    val t = (startValue / denominator).coerceIn(0f, 1f)
    return androidx.compose.ui.geometry.Offset(
        x = start.x + (end.x - start.x) * t,
        y = start.y + (end.y - start.y) * t,
    )
}

private fun dot(
    axis: androidx.compose.ui.geometry.Offset,
    point: androidx.compose.ui.geometry.Offset,
): Float = axis.x * point.x + axis.y * point.y
