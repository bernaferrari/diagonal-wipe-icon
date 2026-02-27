package com.bernaferrari

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import kotlinx.coroutines.delay

private data class ThemeSeed(
    val name: String,
    val color: Color,
    val style: PaletteStyle,
)

private val themeSeedOptions = listOf(
    ThemeSeed("Mint", Color(0xFF00FA9A), PaletteStyle.Expressive),
    ThemeSeed("Gold", Color(0xFFFFD700), PaletteStyle.Expressive),
    ThemeSeed("Salmon", Color(0xFFFF8B7B), PaletteStyle.Expressive),
    ThemeSeed("Azure", Color(0xFF007FFF), PaletteStyle.Expressive),
    ThemeSeed("Rose", Color(0xFFFF007F), PaletteStyle.TonalSpot),
    ThemeSeed("Slate", Color(0xFF708090), PaletteStyle.Neutral),
    ThemeSeed("Charcoal", Color(0xFF36454F), PaletteStyle.Vibrant),
)

@Composable
@Preview
fun App() {
    var showIntro by rememberSaveable { mutableStateOf(false) }
    var globalAnimationMultiplier by rememberSaveable { mutableFloatStateOf(1f) }
    var isGloballyWiped by rememberSaveable { mutableStateOf(false) }
    var isLooping by rememberSaveable { mutableStateOf(false) }
    var selectedSeedIndex by rememberSaveable { mutableIntStateOf(3) }
    val systemDark = isSystemInDarkTheme()
    var isDark by rememberSaveable { mutableStateOf(systemDark) }

    LaunchedEffect(Unit) {
        delay(110)
        showIntro = true
    }

    LaunchedEffect(isLooping, globalAnimationMultiplier) {
        if (!isLooping) {
            isGloballyWiped = false
            return@LaunchedEffect
        }
        val enableDelayMs = autoPlayDelay(
            DiagonalWipeIconDefaults.WipeInDurationMillis,
            globalAnimationMultiplier,
        )
        val disableDelayMs = autoPlayDelay(
            DiagonalWipeIconDefaults.WipeOutDurationMillis,
            globalAnimationMultiplier,
        )
        while (true) {
            isGloballyWiped = true
            delay(enableDelayMs.toLong())
            isGloballyWiped = false
            delay(disableDelayMs.toLong())
        }
    }

    val selectedSeed = themeSeedOptions[selectedSeedIndex]
    val uriHandler = LocalUriHandler.current
    val isSlowMode = globalAnimationMultiplier > 1f

    DynamicMaterialTheme(
        seedColor = selectedSeed.color,
        isDark = isDark,
        specVersion = ColorSpec.SpecVersion.SPEC_2021,
        style = selectedSeed.style,
        animate = true,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Compact SAAS header
                    AnimatedVisibility(
                        visible = showIntro,
                        enter = fadeIn(tween(320, easing = FastOutSlowInEasing)) +
                                slideInVertically(
                                    tween(
                                        320,
                                        easing = FastOutSlowInEasing
                                    )
                                ) { it / 4 },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp)
                                .padding(top = 24.dp, bottom = 16.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column {
                                    Text(
                                        text = "Diagonal Wipe Icons",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                    Text(
                                        text = "Elegant icon transitions for Material Design",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    IconButton(
                                        onClick = { uriHandler.openUri("https://github.com/bernaferrari") },
                                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                    ) {
                                        Icon(
                                            imageVector = GitHubIcon,
                                            contentDescription = "GitHub",
                                            modifier = Modifier.size(22.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }

                                    IconButton(
                                        onClick = { isDark = !isDark },
                                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                    ) {
                                        Icon(
                                            imageVector = if (isDark) Icons.Outlined.LightMode
                                            else Icons.Outlined.DarkMode,
                                            contentDescription = if (isDark) "Light mode" else "Dark mode",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Main Content Grid
                    DiagonalWipeIconGridDemo(
                        animationMultiplier = globalAnimationMultiplier,
                        allIconsWiped = isGloballyWiped,
                        isLooping = isLooping,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    )
                }

                // Floating toolbar - minimal, elevated
                AnimatedVisibility(
                    visible = showIntro,
                    enter = fadeIn(tween(360, 70, FastOutSlowInEasing)) +
                            slideInVertically(tween(360, 70, FastOutSlowInEasing)) { it / 8 },
                    modifier = Modifier.align(Alignment.BottomCenter),
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 4.dp,
                        tonalElevation = 1.dp,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(bottom = 24.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            // Theme colors - alive and responsive
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                themeSeedOptions.forEachIndexed { index, seed ->
                                    ColorDot(
                                        color = seed.color,
                                        isSelected = index == selectedSeedIndex,
                                        onClick = { selectedSeedIndex = index },
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                                    .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                            )

                            // Animation controls - custom styled buttons
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                ToolbarControlButton(
                                    selected = isSlowMode,
                                    onClick = {
                                        globalAnimationMultiplier =
                                            if (isSlowMode) 1f else SlowAnimationMultiplier
                                    },
                                    label = if (isSlowMode) "0.5x" else "1x",
                                    icon = Icons.Outlined.Speed,
                                )

                                ToolbarControlButton(
                                    selected = isLooping,
                                    onClick = {
                                        isLooping = !isLooping
                                        if (!isLooping) isGloballyWiped = false
                                    },
                                    label = "Loop",
                                    icon = if (isLooping) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolbarControlButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            isHovered -> MaterialTheme.colorScheme.surfaceVariant
            else -> MaterialTheme.colorScheme.surface
        },
        tonalElevation = if (isHovered) 2.dp else 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .height(40.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 14.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ColorDot(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // Selected state: larger and more prominent
    // Hover: subtle grow
    // Press: gentle shrink
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.95f
            isSelected -> 1.12f
            isHovered -> 1.06f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    // Animated border width for smooth selection transition
    val borderWidth by animateFloatAsState(
        targetValue = if (isSelected) 3f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(28.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(CircleShape)
            .background(color)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 3.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.White
            )
        }
    }
}

// Custom GitHub icon
private val GitHubIcon: ImageVector
    get() = ImageVector.Builder(
        name = "GitHub",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            // GitHub logo path
            moveTo(12f, 0f)
            curveTo(5.374f, 0f, 0f, 5.373f, 0f, 12f)
            curveTo(0f, 17.302f, 3.438f, 21.8f, 8.207f, 23.387f)
            curveTo(8.806f, 23.498f, 9f, 23.126f, 9f, 22.81f)
            verticalLineTo(20.576f)
            curveTo(5.662f, 21.302f, 4.967f, 19.16f, 4.967f, 19.16f)
            curveTo(4.421f, 17.773f, 3.634f, 17.404f, 3.634f, 17.404f)
            curveTo(2.545f, 16.659f, 3.717f, 16.675f, 3.717f, 16.675f)
            curveTo(4.922f, 16.759f, 5.556f, 17.912f, 5.556f, 17.912f)
            curveTo(6.626f, 19.746f, 8.363f, 19.216f, 9.048f, 18.909f)
            curveTo(9.155f, 18.134f, 9.466f, 17.604f, 9.81f, 17.305f)
            curveTo(7.145f, 17f, 4.343f, 15.971f, 4.343f, 11.374f)
            curveTo(4.343f, 10.063f, 4.812f, 8.993f, 5.579f, 8.153f)
            curveTo(5.455f, 7.85f, 5.044f, 6.629f, 5.696f, 4.977f)
            curveTo(5.696f, 4.977f, 6.704f, 4.655f, 8.997f, 6.207f)
            curveTo(9.954f, 5.941f, 10.98f, 5.808f, 12f, 5.803f)
            curveTo(13.02f, 5.808f, 14.047f, 5.941f, 15.006f, 6.207f)
            curveTo(17.297f, 4.655f, 18.303f, 4.977f, 18.303f, 4.977f)
            curveTo(18.956f, 6.63f, 18.545f, 7.851f, 18.421f, 8.153f)
            curveTo(19.191f, 8.993f, 19.656f, 10.063f, 19.656f, 11.374f)
            curveTo(19.656f, 15.983f, 16.849f, 16.998f, 14.177f, 17.295f)
            curveTo(14.607f, 17.667f, 15f, 18.397f, 15f, 19.517f)
            verticalLineTo(22.81f)
            curveTo(15f, 23.129f, 15.192f, 23.504f, 15.801f, 23.386f)
            curveTo(20.566f, 21.797f, 24f, 17.3f, 24f, 12f)
            curveTo(24f, 5.373f, 18.627f, 0f, 12f, 0f)
            close()
        }
    }.build()
