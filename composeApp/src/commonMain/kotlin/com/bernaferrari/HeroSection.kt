package com.bernaferrari

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Hero section with large animated showcase and Material Design colors.
 */
@Composable
fun HeroSection(
    modifier: Modifier = Modifier,
    selectedSeed: ThemeSeed,
    onOpenHowItWorks: () -> Unit,
    onScrollToGrid: () -> Unit,
    onIconClick: (MaterialWipeIconPair) -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(480.dp)
            .drawBehind {
                drawMeshGradientBackground(backgroundColor, selectedSeed.color)
            },
        contentAlignment = Alignment.Center
    ) {
        // Subtle animated background orbs
        AnimatedOrbs(selectedSeed.color)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // Title
            HeroTitle()

            // Multi-icon showcase - clickable
            HeroIconShowcase(onIconClick = onIconClick)

            // CTA Buttons
            HeroActions(
                onOpenHowItWorks = onOpenHowItWorks
            )
        }
    }
}

@Composable
private fun HeroTitle() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Diagonal Wipe Icons",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Text(
            text = "Elegant morphing transitions for Material Design",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
private fun HeroIconShowcase(
    onIconClick: (MaterialWipeIconPair) -> Unit,
) {
    // Use actual icon pairs from the catalog for consistency
    val iconPairs = remember {
        listOf(
            MaterialWipeIconPair("Visibility Off", Icons.Outlined.Visibility, Icons.Outlined.VisibilityOff),
            MaterialWipeIconPair("Wifi Off", Icons.Outlined.Wifi, Icons.Outlined.WifiOff),
            MaterialWipeIconPair("Mic Off", Icons.Outlined.Mic, Icons.Outlined.MicOff),
            MaterialWipeIconPair("Alarm Off", Icons.Outlined.Alarm, Icons.Outlined.AlarmOff),
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconPairs.forEachIndexed { index, iconPair ->
            HeroAnimatedIcon(
                iconPair = iconPair,
                delayMillis = index * 200,
                onClick = { onIconClick(iconPair) }
            )
        }
    }
}

@Composable
private fun HeroAnimatedIcon(
    iconPair: MaterialWipeIconPair,
    delayMillis: Int,
    onClick: () -> Unit,
) {
    var isWiped by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        while (true) {
            isWiped = true
            delay(1200)
            isWiped = false
            delay(1200)
        }
    }

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.95f
            isHovered -> 1.08f
            isWiped -> 1.03f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "heroIconScale"
    )

    val bgColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        animationSpec = tween(200),
        label = "heroIconBg"
    )

    Box(
        modifier = Modifier
            .size(90.dp)
            .scale(scale)
            .clip(RoundedCornerShape(22.dp))
            .background(bgColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource),
        contentAlignment = Alignment.Center
    ) {
        // Subtle border on hover - using outline color
        if (isHovered) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(
                        width = 1.5.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(22.dp)
                    )
            )
        }

        DiagonalWipeIcon(
            isWiped = isWiped,
            baseIcon = iconPair.enabledIcon,
            wipedIcon = iconPair.disabledIcon,
            baseTint = MaterialTheme.colorScheme.primary,
            wipedTint = MaterialTheme.colorScheme.secondary,
            contentDescription = materialWipeIconLabel(iconPair.label),
            modifier = Modifier.size(44.dp),
            motion = DiagonalWipeIconDefaults.gentle()
        )
    }
}

@Composable
private fun HeroActions(
    onOpenHowItWorks: () -> Unit,
) {
    FilledTonalButton(
        onClick = onOpenHowItWorks,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
    ) {
        Text(
            text = "How it works",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun AnimatedOrbs(accentColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbs")

    val orb1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orb1"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Single subtle orb
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.06f),
                    Color.Transparent
                ),
                center = Offset(
                    centerX + cos(orb1Offset) * 120f,
                    centerY + sin(orb1Offset) * 80f
                ),
                radius = 250f
            ),
            radius = 250f,
            center = Offset(
                centerX + cos(orb1Offset) * 120f,
                centerY + sin(orb1Offset) * 80f
            )
        )
    }
}

private fun DrawScope.drawMeshGradientBackground(backgroundColor: Color, seedColor: Color) {
    // Base background uses Material theme
    drawRect(backgroundColor)

    // Subtle radial gradient from center - very faint
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(
                seedColor.copy(alpha = 0.025f),
                Color.Transparent
            ),
            center = Offset(size.width / 2, size.height / 2),
            radius = size.width * 0.7f
        )
    )
}
