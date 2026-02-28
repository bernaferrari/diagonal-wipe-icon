package com.bernaferrari.diagonalwipeicon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class ThemeSeed(
    val name: String,
    val color: Color,
    val style: PaletteStyle,
)

private val themeSeedOptions = listOf(
    ThemeSeed("Salmon", Color(0xFFFF8B7B), PaletteStyle.Expressive),
    ThemeSeed("Gold", Color(0xFFFFD700), PaletteStyle.Expressive),
    ThemeSeed("Mint", Color(0xFF00FA9A), PaletteStyle.Expressive),
    ThemeSeed("Azure", Color(0xFF007FFF), PaletteStyle.Expressive),
    ThemeSeed("Rose", Color(0xFFFF007F), PaletteStyle.Expressive),
    ThemeSeed("Slate", Color(0xFF708090), PaletteStyle.Neutral),
)

@Composable
fun App() {
    var showIntro by remember { mutableStateOf(false) }
    var globalAnimationMultiplier by remember { mutableFloatStateOf(1f) }
    var isGloballyWiped by remember { mutableStateOf(false) }
    var isLooping by remember { mutableStateOf(false) }
    var selectedSeedIndex by remember { mutableIntStateOf(3) }
    val systemDark = isSystemInDarkTheme()
    var isDark by remember { mutableStateOf(systemDark) }
    var showHowItWorks by remember { mutableStateOf(false) }
    var howItWorksDirection by remember { mutableStateOf(WipeDirection.TopLeftToBottomRight) }
    var heroSelectedIcon by remember { mutableStateOf<MaterialWipeIconPair?>(null) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        delay(150)
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

    // Title opacity based on scroll position (fade in as user scrolls)
    val titleAlpha by remember { derivedStateOf { (scrollState.value / 300f).coerceIn(0f, 1f) } }

    DynamicMaterialTheme(
        seedColor = selectedSeed.color,
        isDark = isDark,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
        style = selectedSeed.style,
        animate = true,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Subtle animated background
            AnimatedMeshBackground(selectedSeed.color, isDark)

            Scaffold(
                topBar = {
                AnimatedTopBar(
                    titleAlpha = titleAlpha,
                    isDark = isDark,
                    onToggleDark = { isDark = !isDark },
                    onOpenGitHub = { uriHandler.openUri("https://github.com/bernaferrari") }
                )
                },
                containerColor = Color.Transparent
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        // Hero Section
                        AnimatedVisibility(
                            visible = showIntro,
                            enter = fadeIn(tween(500, easing = EaseOut)) +
                                    slideInVertically(tween(500, easing = EaseOut)) { it / 4 }
                        ) {
                            HeroSection(
                                selectedSeed = selectedSeed,
                                onOpenHowItWorks = { showHowItWorks = true },
                                onIconClick = { iconPair ->
                                    heroSelectedIcon = iconPair
                                }
                            )
                        }

                        // Main Content Grid
                        DiagonalWipeIconGridDemo(
                            animationMultiplier = globalAnimationMultiplier,
                            allIconsWiped = isGloballyWiped,
                            isLooping = isLooping,
                            accentColor = selectedSeed.color,
                            externalSelectedIcon = heroSelectedIcon,
                            onExternalSelectedIconConsumed = { heroSelectedIcon = null },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Space for bottom toolbar
                        Spacer(modifier = Modifier.height(100.dp))
                    }

                    // Floating bottom toolbar
                    AnimatedVisibility(
                        visible = showIntro,
                        enter = fadeIn(tween(400, delayMillis = 600)) +
                                slideInVertically(tween(400, delayMillis = 600)) { it / 2 },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        BottomToolbar(
                            themeSeedOptions = themeSeedOptions,
                            selectedSeedIndex = selectedSeedIndex,
                            onSeedSelected = { selectedSeedIndex = it },
                            isSlowMode = isSlowMode,
                            onToggleSlowMode = {
                                globalAnimationMultiplier = if (isSlowMode) 1f else SlowAnimationMultiplier
                            },
                            isLooping = isLooping,
                            onToggleLoop = {
                                isLooping = !isLooping
                                if (!isLooping) isGloballyWiped = false
                            }
                        )
                    }
                }
            }

            // How it works dialog
            if (showHowItWorks) {
                HowItWorksDialog(
                    direction = howItWorksDirection,
                    onDirectionChange = { howItWorksDirection = it },
                    onDismiss = { showHowItWorks = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedTopBar(
    titleAlpha: Float,
    isDark: Boolean,
    onToggleDark: () -> Unit,
    onOpenGitHub: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Diagonal Wipe Icons",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.alpha(titleAlpha)
            )
        },
        navigationIcon = { },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(
                    onClick = onOpenGitHub,
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = GitHubIcon,
                        contentDescription = "GitHub",
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(
                    onClick = onToggleDark,
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = if (isDark) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                        contentDescription = if (isDark) "Light mode" else "Dark mode",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
        )
    )
}

@Composable
private fun BottomToolbar(
    themeSeedOptions: List<ThemeSeed>,
    selectedSeedIndex: Int,
    onSeedSelected: (Int) -> Unit,
    isSlowMode: Boolean,
    onToggleSlowMode: () -> Unit,
    isLooping: Boolean,
    onToggleLoop: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp,
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Theme colors
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                themeSeedOptions.forEachIndexed { index, seed ->
                    ColorDot(
                        label = seed.name,
                        color = seed.color,
                        isSelected = index == selectedSeedIndex,
                        onClick = { onSeedSelected(index) }
                    )
                }
            }

            // Divider
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            )

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToolbarButton(
                    selected = isSlowMode,
                    onClick = onToggleSlowMode,
                    icon = Icons.Outlined.Speed,
                    label = if (isSlowMode) "0.5x" else "1x"
                )

                ToolbarButton(
                    selected = isLooping,
                    onClick = onToggleLoop,
                    icon = if (isLooping) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                    label = if (isLooping) "Stop" else "Loop"
                )
            }
        }
    }
}

@Composable
private fun ToolbarButton(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "buttonScale"
    )

    val containerColor = when {
        selected -> MaterialTheme.colorScheme.primaryContainer
        isHovered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        else -> Color.Transparent
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        modifier = Modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                ),
                color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ColorDot(
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.9f
            isSelected -> 1.12f
            isHovered -> 1.06f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "colorDotScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(44.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .semantics {
                contentDescription = "$label theme"
                selected = isSelected
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.RadioButton,
                onClick = onClick
            )
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
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
                            width = 2.5.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            shape = CircleShape
                        )
                    } else Modifier
                )
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = if (color.luminance() > 0.5f) Color.Black else Color.White
                )
            }
        }
    }
}

@Composable
private fun AnimatedMeshBackground(seedColor: Color, isDark: Boolean) {
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
            .drawBehind {
                val baseColor = if (isDark) Color(0xFF0D0D0D) else Color(0xFFFAFAFA)
                drawRect(baseColor)

                // Single large subtle orb
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

// Custom GitHub icon
private val GitHubIcon: ImageVector = ImageVector.Builder(
        name = "GitHub",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
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

// Import necessary extensions
private fun Color.luminance(): Float {
    val r = red
    val g = green
    val b = blue
    return 0.2126f * r + 0.7152f * g + 0.0722f * b
}
