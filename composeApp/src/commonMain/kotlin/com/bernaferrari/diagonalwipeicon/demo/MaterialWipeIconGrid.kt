package com.bernaferrari.diagonalwipeicon.demo

import com.bernaferrari.diagonalwipeicon.DiagonalWipeIcon
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIconDefaults
import com.bernaferrari.diagonalwipeicon.WipeIconSource

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

data class MaterialWipeIconPair(
    val label: String,
    val enabledIcon: WipeIconSource,
    val disabledIcon: WipeIconSource,
    val enabledCodeIconName: String? = null,
    val disabledCodeIconName: String? = null,
)

data class MaterialWipeIconSection(
    val title: String,
    val subtitle: String,
    val icons: List<MaterialWipeIconPair>,
)

internal fun materialWipeIconLabel(rawLabel: String): String = rawLabel

@Composable
fun DiagonalWipeIconGridDemo(
    modifier: Modifier = Modifier,
    animationMultiplier: Float = 1f,
    allIconsWiped: Boolean = false,
    isLooping: Boolean = false,
    accentColor: Color = Color(0xFF007FFF),
    externalSelectedIcon: MaterialWipeIconPair? = null,
    onExternalSelectedIconConsumed: () -> Unit = {},
    headerContent: (@Composable () -> Unit)? = null,
    onScrollProgressChanged: (Float) -> Unit = {},
) {
    var selectedIcon by remember { mutableStateOf<MaterialWipeIconPair?>(null) }
    val listState = rememberLazyListState()

    LaunchedEffect(externalSelectedIcon) {
        externalSelectedIcon?.let {
            selectedIcon = it
            onExternalSelectedIconConsumed()
        }
    }

    LaunchedEffect(listState, onScrollProgressChanged) {
        snapshotFlow {
            if (listState.firstVisibleItemIndex > 0) {
                1f
            } else {
                (listState.firstVisibleItemScrollOffset / UiTokens.ScrollProgressOffsetPx)
                    .coerceIn(0f, 1f)
            }
        }
            .map { progress ->
                (progress * UiTokens.ScrollProgressQuantizationSteps).toInt() /
                        UiTokens.ScrollProgressQuantizationSteps
            }
            .distinctUntilChanged()
            .collect { progress ->
                onScrollProgressChanged(progress)
            }
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
    ) {
        val viewportWidth = maxWidth
        val horizontalPadding = if (viewportWidth > UiTokens.GridMaxContentWidth) {
            ((viewportWidth - UiTokens.GridMaxContentWidth) / 2) + UiTokens.PageHorizontalPadding
        } else {
            UiTokens.PageHorizontalPadding
        }
        val contentWidth =
            (viewportWidth - horizontalPadding * 2f).coerceAtLeast(UiTokens.GridItemSlotWidth)
        val columns = (contentWidth / UiTokens.GridItemSlotWidth).toInt().coerceAtLeast(1)

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (headerContent != null) {
                item(key = "external-header") {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        headerContent()
                    }
                }
            }

            iconSections.forEachIndexed { sectionIndex, section ->
                item(key = "header-${section.title}") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                    ) {
                        MaterialWipeIconSectionHeader(
                            section = section,
                            topPadding = if (sectionIndex == 0) 0.dp else 16.dp,
                        )
                    }
                }

                val iconRows = section.icons.chunked(columns)
                itemsIndexed(
                    items = iconRows,
                    key = { rowIndex, _ -> "icons-${section.title}-$rowIndex" },
                ) { _, rowIcons ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                        ) {
                            rowIcons.forEach { iconPair ->
                                DiagonalWipeIconGridItem(
                                    iconPair = iconPair,
                                    animationMultiplier = animationMultiplier,
                                    allIconsWiped = allIconsWiped,
                                    isLooping = isLooping,
                                    accentColor = accentColor,
                                    onOpen = { selectedIcon = iconPair },
                                )
                            }
                        }
                    }
                }

                item(key = "gap-${section.title}") {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    selectedIcon?.let { icon ->
        IconPreviewDialog(
            iconPair = icon,
            baseAnimationMultiplier = animationMultiplier,
            onDismiss = { selectedIcon = null },
        )
    }
}

@Composable
internal fun MaterialWipeIconSectionHeader(
    section: MaterialWipeIconSection,
    topPadding: Dp = 16.dp,
    bottomPadding: Dp = 16.dp,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding, bottom = bottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = section.subtitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
internal fun DiagonalWipeIconGridItem(
    iconPair: MaterialWipeIconPair,
    animationMultiplier: Float,
    allIconsWiped: Boolean,
    isLooping: Boolean,
    accentColor: Color,
    onOpen: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val effectiveHover = isHovered
    var hasLoadedWipedPainter by remember { mutableStateOf(false) }

    LaunchedEffect(effectiveHover, isLooping) {
        if (effectiveHover || isLooping) {
            hasLoadedWipedPainter = true
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    val iconBgColor by animateColorAsState(
        targetValue = when {
            isPressed -> accentColor.copy(alpha = 0.2f)
            effectiveHover -> accentColor.copy(alpha = 0.12f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        },
        animationSpec = tween(200),
        label = "iconBg"
    )

    val iconBorderColor by animateColorAsState(
        targetValue = when {
            isPressed -> accentColor
            effectiveHover -> accentColor.copy(alpha = 0.5f)
            else -> Color.Transparent
        },
        animationSpec = tween(200),
        label = "iconBorder"
    )

    Column(
        modifier = Modifier
            .width(UiTokens.GridItemSlotWidth)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onOpen,
            )
            .padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(iconBgColor)
                .border(
                    width = if (effectiveHover || isPressed) 1.5.dp else 0.dp,
                    color = iconBorderColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            val basePainter = iconPair.enabledIcon.painter()
            val wipedPainter = if (hasLoadedWipedPainter || effectiveHover || isLooping) {
                iconPair.disabledIcon.painter()
            } else {
                basePainter
            }
            val shouldWipe = if (isLooping) allIconsWiped else effectiveHover
            val stiffness =
                if (animationMultiplier > 1f) Spring.StiffnessVeryLow else Spring.StiffnessLow

            DiagonalWipeIcon(
                isWiped = shouldWipe,
                basePainter = basePainter,
                wipedPainter = wipedPainter,
                baseTint = MaterialTheme.colorScheme.primary,
                wipedTint = MaterialTheme.colorScheme.secondary,
                contentDescription = materialWipeIconLabel(iconPair.label),
                modifier = Modifier.fillMaxSize(),
                motion = DiagonalWipeIconDefaults.spring(
                    wipeInStiffness = stiffness,
                    wipeOutStiffness = stiffness,
                    wipeInDampingRatio = Spring.DampingRatioNoBouncy,
                    wipeOutDampingRatio = Spring.DampingRatioNoBouncy,
                ),
            )
        }

        Text(
            text = materialWipeIconLabel(iconPair.label),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (effectiveHover) FontWeight.SemiBold else FontWeight.Medium
            ),
            color = when {
                isPressed -> accentColor
                effectiveHover -> MaterialTheme.colorScheme.onSurface
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
