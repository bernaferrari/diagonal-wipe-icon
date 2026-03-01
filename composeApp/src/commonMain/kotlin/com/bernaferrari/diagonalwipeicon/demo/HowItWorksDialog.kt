package com.bernaferrari.diagonalwipeicon.demo

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIconAtProgress
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIconDefaults
import com.bernaferrari.diagonalwipeicon.DiagonalWipeMotion
import com.bernaferrari.diagonalwipeicon.SlowAnimationMultiplier
import com.bernaferrari.diagonalwipeicon.WipeDirection
import com.bernaferrari.diagonalwipeicon.WipeIconSource
import com.bernaferrari.diagonalwipeicon.autoPlayDelay
import com.bernaferrari.diagonalwipeicon.buildWipeBoundaryLine
import com.bernaferrari.diagonalwipeicon.motionSpec
import kotlinx.coroutines.delay

private data class WipeDirectionOption(
    val direction: WipeDirection,
    val label: String,
)

private val howItWorksDirectionOptions = listOf(
    WipeDirectionOption(WipeDirection.TopLeftToBottomRight, "Top-left to bottom-right"),
    WipeDirectionOption(WipeDirection.TopRightToBottomLeft, "Top-right to bottom-left"),
    WipeDirectionOption(WipeDirection.BottomLeftToTopRight, "Bottom-left to top-right"),
    WipeDirectionOption(WipeDirection.BottomRightToTopLeft, "Bottom-right to top-left"),
    WipeDirectionOption(WipeDirection.TopToBottom, "Top to bottom"),
    WipeDirectionOption(WipeDirection.BottomToTop, "Bottom to top"),
    WipeDirectionOption(WipeDirection.LeftToRight, "Left to right"),
    WipeDirectionOption(WipeDirection.RightToLeft, "Right to left"),
)

private val howItWorksDirectionLabels =
    howItWorksDirectionOptions.associate { it.direction to it.label }

@Composable
internal fun HowItWorksDialog(
    direction: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
    onDismiss: () -> Unit,
) {
    val isWiped = rememberLoopingWipe()
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 0.dp,
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(0.94f),
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Text(
                    text = "How it works",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "SF Symbols has built-in symbol transitions. Material Icons does not, so this gives you the same kind of morph without hand-drawing a custom vector.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                HowItWorksDirectionSelector(
                    direction = direction,
                    onDirectionChange = onDirectionChange,
                )
                HowItWorksFlow(
                    iconPair = howItWorksPair,
                    isWiped = isWiped,
                    direction = direction,
                    tileHeight = 100.dp,
                    squareSize = 64.dp,
                    animationMultiplier = SlowAnimationMultiplier,
                )
                Text(
                    text = "Use two icons and one animated mask. It is much easier and faster than building and maintaining custom vector paths by hand.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
private fun HowItWorksFlow(
    iconPair: MaterialWipeIconPair,
    isWiped: Boolean,
    direction: WipeDirection,
    tileHeight: Dp,
    squareSize: Dp,
    animationMultiplier: Float = 1f,
) {
    val allowedColor = MaterialTheme.colorScheme.primary
    val blockedColor = MaterialTheme.colorScheme.secondary
    val stiffness = if (animationMultiplier > 1f) Spring.StiffnessVeryLow else Spring.StiffnessLow
    val howItWorksMotion = DiagonalWipeIconDefaults.spring(
        direction = direction,
        wipeInStiffness = stiffness,
        wipeOutStiffness = stiffness,
        wipeInDampingRatio = Spring.DampingRatioNoBouncy,
        wipeOutDampingRatio = Spring.DampingRatioNoBouncy,
    )
    val progress = rememberWipeProgress(isWiped = isWiped, motion = howItWorksMotion)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier.weight(3f),
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HowItWorksStep(
                    title = "Base",
                    frameHeight = tileHeight,
                    modifier = Modifier.weight(1f),
                ) {
                    SingleIconWipeLayerPreview(
                        progress = progress,
                        icon = iconPair.enabledIcon,
                        tint = allowedColor,
                        entersOnReveal = false,
                        motion = howItWorksMotion,
                        modifier = Modifier.size(squareSize),
                    )
                }

                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                HowItWorksStep(
                    title = "Overlay",
                    frameHeight = tileHeight,
                    modifier = Modifier.weight(1f),
                ) {
                    SingleIconWipeLayerPreview(
                        progress = progress,
                        icon = iconPair.disabledIcon,
                        tint = blockedColor,
                        entersOnReveal = true,
                        motion = howItWorksMotion,
                        modifier = Modifier.size(squareSize),
                    )
                }

                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                HowItWorksStep(
                    title = "Mask",
                    frameHeight = tileHeight,
                    modifier = Modifier.weight(1f),
                ) {
                    MaskPreviewWithIcons(
                        progress = progress,
                        baseIcon = iconPair.enabledIcon,
                        overlayIcon = iconPair.disabledIcon,
                        baseTint = allowedColor,
                        overlayTint = blockedColor,
                        motion = howItWorksMotion,
                        modifier = Modifier.size(squareSize),
                    )
                }
            }
        }

        Text(
            text = "=",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier.weight(1f),
        ) {
            Box(
                modifier = Modifier.padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                HowItWorksStep(
                    title = "Result",
                    frameHeight = tileHeight,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    DiagonalWipeIconAtProgress(
                        progress = progress,
                        basePainter = iconPair.enabledIcon.painter(),
                        wipedPainter = iconPair.disabledIcon.painter(),
                        baseTint = allowedColor,
                        wipedTint = blockedColor,
                        contentDescription = materialWipeIconLabel(iconPair.label),
                        modifier = Modifier.size(squareSize),
                        motion = howItWorksMotion,
                    )
                }
            }
        }
    }
}

@Composable
private fun rememberWipeProgress(
    isWiped: Boolean,
    motion: DiagonalWipeMotion,
): Float {
    val transition = updateTransition(targetState = isWiped, label = "howItWorksSharedWipe")
    val progress by transition.animateFloat(
        transitionSpec = { motionSpec(false isTransitioningTo true, motion) },
        label = "howItWorksSharedProgress",
    ) { wiped ->
        if (wiped) 1f else 0f
    }
    return progress.coerceIn(0f, 1f)
}

@Composable
private fun HowItWorksDirectionSelector(
    direction: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
) {
    val label = howItWorksDirectionLabels[direction] ?: "Top-left to bottom-right"

    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 0.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DirectionArrowGrid(
                selectedDirection = direction,
                onDirectionChange = onDirectionChange,
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun DirectionArrowGrid(
    selectedDirection: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
) {
    Column(
        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand, overrideDescendants = true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DirectionIconButton(
                icon = MaterialSymbolIcons.NorthWest,
                contentDescription = "Bottom-right to top-left",
                selected = selectedDirection == WipeDirection.BottomRightToTopLeft,
                onClick = { onDirectionChange(WipeDirection.BottomRightToTopLeft) },
            )
            DirectionIconButton(
                icon = MaterialSymbolIcons.KeyboardArrowUp,
                contentDescription = "Bottom to top",
                selected = selectedDirection == WipeDirection.BottomToTop,
                onClick = { onDirectionChange(WipeDirection.BottomToTop) },
            )
            DirectionIconButton(
                icon = MaterialSymbolIcons.NorthEast,
                contentDescription = "Bottom-left to top-right",
                selected = selectedDirection == WipeDirection.BottomLeftToTopRight,
                onClick = { onDirectionChange(WipeDirection.BottomLeftToTopRight) },
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DirectionIconButton(
                icon = MaterialSymbolIcons.KeyboardArrowLeft,
                contentDescription = "Right to left",
                selected = selectedDirection == WipeDirection.RightToLeft,
                onClick = { onDirectionChange(WipeDirection.RightToLeft) },
            )
            Box(modifier = Modifier.size(44.dp))
            DirectionIconButton(
                icon = MaterialSymbolIcons.KeyboardArrowRight,
                contentDescription = "Left to right",
                selected = selectedDirection == WipeDirection.LeftToRight,
                onClick = { onDirectionChange(WipeDirection.LeftToRight) },
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DirectionIconButton(
                icon = MaterialSymbolIcons.SouthWest,
                contentDescription = "Top-right to bottom-left",
                selected = selectedDirection == WipeDirection.TopRightToBottomLeft,
                onClick = { onDirectionChange(WipeDirection.TopRightToBottomLeft) },
            )
            DirectionIconButton(
                icon = MaterialSymbolIcons.KeyboardArrowDown,
                contentDescription = "Top to bottom",
                selected = selectedDirection == WipeDirection.TopToBottom,
                onClick = { onDirectionChange(WipeDirection.TopToBottom) },
            )
            DirectionIconButton(
                icon = MaterialSymbolIcons.SouthEast,
                contentDescription = "Top-left to bottom-right",
                selected = selectedDirection == WipeDirection.TopLeftToBottomRight,
                onClick = { onDirectionChange(WipeDirection.TopLeftToBottomRight) },
            )
        }
    }
}

@Composable
private fun DirectionIconButton(
    icon: WipeIconSource,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.92f
            isHovered -> 1.08f
            else -> 1f
        },
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(
            alpha = 0.3f
        ),
        modifier = Modifier
            .size(44.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .semantics { this.selected = selected }
            .pointerHoverIcon(PointerIcon.Hand, overrideDescendants = true)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.RadioButton,
                onClick = onClick,
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Icon(
                painter = icon.painter(),
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
                tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun HowItWorksStep(
    title: String,
    frameHeight: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(frameHeight)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

@Composable
private fun SingleIconWipeLayerPreview(
    progress: Float,
    icon: WipeIconSource,
    tint: Color,
    entersOnReveal: Boolean,
    motion: DiagonalWipeMotion,
    modifier: Modifier = Modifier,
) {
    val painter = icon.painter()

    DiagonalWipeIconAtProgress(
        progress = progress,
        basePainter = painter,
        wipedPainter = painter,
        baseTint = if (entersOnReveal) Color.Transparent else tint,
        wipedTint = if (entersOnReveal) tint else Color.Transparent,
        contentDescription = null,
        motion = motion,
        modifier = modifier.padding(2.dp),
    )
}

@Composable
private fun MaskPreviewWithIcons(
    progress: Float,
    baseIcon: WipeIconSource,
    overlayIcon: WipeIconSource,
    baseTint: Color,
    overlayTint: Color,
    motion: DiagonalWipeMotion,
    modifier: Modifier = Modifier,
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val rotation = clampedProgress * 180f

        if (rotation < 90f) {
            Icon(
                painter = baseIcon.painter(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = rotation
                        alpha = 1f - (rotation / 90f)
                    },
                tint = baseTint,
            )
        }

        if (rotation > 90f) {
            Icon(
                painter = overlayIcon.painter(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = rotation - 180f
                        alpha = (rotation - 90f) / 90f
                    },
                tint = overlayTint,
            )
        }

        val boundaryColor = MaterialTheme.colorScheme.tertiary
        if (clampedProgress in 0.2f..0.8f) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val line = buildWipeBoundaryLine(
                    width = size.width,
                    height = size.height,
                    progress = clampedProgress,
                    direction = motion.direction,
                ) ?: return@Canvas
                drawLine(
                    color = boundaryColor.copy(alpha = 0.8f),
                    start = line.first,
                    end = line.second,
                    strokeWidth = 2.dp.toPx(),
                )
            }
        }
    }
}

@Composable
private fun rememberLoopingWipe(): Boolean {
    var blocked by remember { mutableStateOf(false) }
    val enableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeInDurationMillis,
        SlowAnimationMultiplier,
    )
    val disableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeOutDurationMillis,
        SlowAnimationMultiplier,
    )

    LaunchedEffect(enableDelay, disableDelay) {
        while (true) {
            blocked = true
            delay(enableDelay.toLong())
            blocked = false
            delay(disableDelay.toLong())
        }
    }

    return blocked
}
