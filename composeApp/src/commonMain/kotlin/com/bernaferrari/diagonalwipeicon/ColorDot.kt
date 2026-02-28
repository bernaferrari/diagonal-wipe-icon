package com.bernaferrari.diagonalwipeicon

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
internal fun ColorDot(
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // Rounded rect ↔ circle morph
    val cornerFraction by animateFloatAsState(
        targetValue = if (isSelected) 0.5f else 0.25f,
        animationSpec = spring(dampingRatio = 0.70f, stiffness = 520f),
        label = "cornerFraction",
    )

    // Swatch grows into its full size when selected
    val orbScale by animateFloatAsState(
        targetValue = if (isSelected) 1.0f else 0.84f,
        animationSpec = spring(dampingRatio = 0.56f, stiffness = 600f),
        label = "orbScale",
    )

    // Gentle press/hover feedback — separate from selection scale
    val pressScale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.93f
            isHovered -> 1.04f
            else -> 1.0f
        },
        animationSpec = spring(dampingRatio = 0.60f, stiffness = 700f),
        label = "pressScale",
    )

    // Playful tilt when selected, icon counter-rotates to stay upright
    val rotation by animateFloatAsState(
        targetValue = if (isSelected) 8f else 0f,
        animationSpec = spring(dampingRatio = 0.66f, stiffness = 420f),
        label = "rotation",
    )

    // Glow halo behind the swatch
    val glowAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(220),
        label = "glowAlpha",
    )

    val checkAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(180),
        label = "checkAlpha",
    )

    val checkTint = if (color.swatchLuminance() > 0.5f) Color.Black.copy(alpha = 0.75f) else Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(38.dp)
            .graphicsLayer { scaleX = pressScale; scaleY = pressScale }
            .semantics {
                contentDescription = "$label theme"
                selected = isSelected
            }
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand),
    ) {
        // Glow — intentionally overflows touch bounds
        Box(
            modifier = Modifier
                .size(54.dp)
                .graphicsLayer { alpha = glowAlpha }
                .drawBehind {
                    drawCircle(
                        color = color.copy(alpha = 0.38f),
                        radius = size.minDimension * 0.5f,
                    )
                },
        )

        // Color swatch
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer {
                    scaleX = orbScale
                    scaleY = orbScale
                    rotationZ = rotation
                }
                .clip(RoundedCornerShape(percent = (cornerFraction * 100).roundToInt()))
                .background(color),
        ) {
            // Gloss highlight
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White.copy(alpha = 0.22f), Color.Transparent),
                            start = Offset.Zero,
                            end = Offset(56f, 56f),
                        ),
                    ),
            )
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .graphicsLayer {
                        alpha = checkAlpha
                        rotationZ = -rotation
                    },
                tint = checkTint,
            )
        }
    }
}

private fun Color.swatchLuminance(): Float {
    return 0.2126f * red + 0.7152f * green + 0.0722f * blue
}
