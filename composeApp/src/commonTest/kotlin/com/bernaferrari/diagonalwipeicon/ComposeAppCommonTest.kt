package com.bernaferrari.diagonalwipeicon

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorizedFiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ComposeAppCommonTest {

    @Test
    fun motionSpec_usesWipeInSpec_forWipeInTransition() {
        val inSpec = TaggedSpec("in", tween(durationMillis = 111))
        val outSpec = TaggedSpec("out", spring())
        val motion = DiagonalWipeMotion(
            wipeInSpec = inSpec,
            wipeOutSpec = outSpec,
        )

        assertSame(inSpec, motionSpec(isWipingIn = true, motion = motion))
        assertSame(outSpec, motionSpec(isWipingIn = false, motion = motion))
    }

    @Test
    fun motion_rejectsNegativeSeamOverlap() {
        assertFailsWith<IllegalArgumentException> {
            DiagonalWipeMotion(seamOverlapPx = -0.1f)
        }
    }

    @Test
    fun presets_applyRequestedDirection() {
        val direction = WipeDirection.LeftToRight

        assertEquals(direction, DiagonalWipeIconDefaults.snappy(direction).direction)
        assertEquals(direction, DiagonalWipeIconDefaults.gentle(direction).direction)
        assertEquals(direction, DiagonalWipeIconDefaults.expressive(direction).direction)
    }

    @Test
    fun tweenBuilder_acceptsZeroDurations() {
        val motion = DiagonalWipeIconDefaults.tween(
            wipeInDurationMillis = 0,
            wipeOutDurationMillis = 0,
        )

        assertEquals(WipeDirection.TopLeftToBottomRight, motion.direction)
    }

    @Test
    fun tweenBuilder_rejectsNegativeDurations() {
        assertFailsWith<IllegalArgumentException> {
            DiagonalWipeIconDefaults.tween(wipeInDurationMillis = -1)
        }
        assertFailsWith<IllegalArgumentException> {
            DiagonalWipeIconDefaults.tween(wipeOutDurationMillis = -1)
        }
    }

    @Test
    fun wipeTravelDistance_matchesExpectedCardinalAndDiagonalRanges() {
        val width = 120f
        val height = 80f

        assertEquals(width, wipeTravelDistance(width, height, WipeDirection.LeftToRight))
        assertEquals(height, wipeTravelDistance(width, height, WipeDirection.TopToBottom))
        assertEquals(width + height, wipeTravelDistance(width, height, WipeDirection.TopLeftToBottomRight))
    }

    @Test
    fun buildWipeBoundaryLine_returnsNullAtExtremes_andLineInsideBoundsMidway() {
        val width = 100f
        val height = 60f

        assertEquals(
            null,
            buildWipeBoundaryLine(width, height, progress = 0f, direction = WipeDirection.LeftToRight),
        )
        assertEquals(
            null,
            buildWipeBoundaryLine(width, height, progress = 1f, direction = WipeDirection.LeftToRight),
        )

        val middle = buildWipeBoundaryLine(
            width = width,
            height = height,
            progress = 0.5f,
            direction = WipeDirection.LeftToRight,
        )
        assertNotNull(middle)
        assertTrue(middle.first.x in 0f..width)
        assertTrue(middle.second.x in 0f..width)
        assertTrue(middle.first.y in 0f..height)
        assertTrue(middle.second.y in 0f..height)
    }
}

private class TaggedSpec(
    val id: String,
    private val delegate: FiniteAnimationSpec<Float>,
) : FiniteAnimationSpec<Float> {
    override fun <V : AnimationVector> vectorize(
        converter: TwoWayConverter<Float, V>,
    ): VectorizedFiniteAnimationSpec<V> = delegate.vectorize(converter)
    override fun toString(): String = "TaggedSpec($id)"
}
