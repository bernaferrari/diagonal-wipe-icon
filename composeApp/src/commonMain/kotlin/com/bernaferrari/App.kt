package com.bernaferrari

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SlowMotionVideo
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private data class ThemeSeed(
    val name: String,
    val color: Color,
)

private val themeSeedOptions = listOf(
    ThemeSeed("Forest", Color(0xFF2F855A)),
    ThemeSeed("Aegean", Color(0xFF2E6F95)),
    ThemeSeed("Terracotta", Color(0xFFC56B4E)),
    ThemeSeed("Honey", Color(0xFFE0A13E)),
    ThemeSeed("Cranberry", Color(0xFFB44C60)),
    ThemeSeed("Teal", Color(0xFF137D73)),
    ThemeSeed("Iris", Color(0xFF5C6AC4)),
)

@Composable
@Preview
fun App() {
    var showIntro by rememberSaveable { mutableStateOf(false) }
    var globalAnimationMultiplier by rememberSaveable { mutableFloatStateOf(1f) }
    var allBlocked by rememberSaveable { mutableStateOf(false) }
    var isLooping by rememberSaveable { mutableStateOf(false) }
    var selectedSeedIndex by rememberSaveable { mutableIntStateOf(0) }
    val systemDark = isSystemInDarkTheme()
    var isDark by rememberSaveable { mutableStateOf(systemDark) }

    LaunchedEffect(Unit) {
        delay(110)
        showIntro = true
    }

    LaunchedEffect(isLooping, globalAnimationMultiplier) {
        if (!isLooping) {
            allBlocked = false
            return@LaunchedEffect
        }
        val enableDelayMs = autoPlayDelay(
            DiagonalWipeIconDefaults.EnableDurationMillis,
            globalAnimationMultiplier,
        )
        val disableDelayMs = autoPlayDelay(
            DiagonalWipeIconDefaults.DisableDurationMillis,
            globalAnimationMultiplier,
        )
        while (true) {
            allBlocked = true
            delay(enableDelayMs.toLong())
            allBlocked = false
            delay(disableDelayMs.toLong())
        }
    }

    val selectedSeed = themeSeedOptions[selectedSeedIndex]
    val sectionAlignedStartInset = 16.dp
    val uriHandler = LocalUriHandler.current

    DynamicMaterialTheme(
        seedColor = selectedSeed.color,
        isDark = isDark,
        style = PaletteStyle.Expressive,
        animate = true,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                AnimatedVisibility(
                    visible = showIntro,
                    enter = fadeIn(tween(320, easing = FastOutSlowInEasing)) +
                            slideInVertically(tween(320, easing = FastOutSlowInEasing)) { it / 4 },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = sectionAlignedStartInset),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Diagonal Wipe Icons",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f),
                        )
                        TextButton(onClick = { uriHandler.openUri("https://github.com/bernaferrari") }) {
                            Icon(
                                imageVector = Icons.Outlined.Code,
                                contentDescription = "Open GitHub profile",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(
                                text = "bernaferrari",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        IconButton(onClick = { isDark = !isDark }) {
                            Icon(
                                imageVector = if (isDark) Icons.Outlined.LightMode
                                else Icons.Outlined.DarkMode,
                                contentDescription = if (isDark) "Switch to light mode"
                                else "Switch to dark mode",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                AnimatedVisibility(
                    visible = showIntro,
                    enter = fadeIn(tween(360, 70, FastOutSlowInEasing)) +
                            slideInVertically(tween(360, 70, FastOutSlowInEasing)) { it / 8 },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = sectionAlignedStartInset),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            themeSeedOptions.forEachIndexed { index, seed ->
                                PaletteDot(
                                    color = seed.color,
                                    isSelected = index == selectedSeedIndex,
                                    onClick = { selectedSeedIndex = index },
                                )
                            }
                        }

                        FilterChip(
                            selected = globalAnimationMultiplier > 1f,
                            onClick = {
                                globalAnimationMultiplier =
                                    if (globalAnimationMultiplier == 1f) SlowAnimationMultiplier else 1f
                            },
                            label = {
                                Icon(
                                    imageVector = Icons.Outlined.SlowMotionVideo,
                                    contentDescription = "Slow mode",
                                    modifier = Modifier.size(18.dp),
                                )
                            },
                        )

                        FilterChip(
                            selected = isLooping,
                            onClick = {
                                isLooping = !isLooping
                                if (!isLooping) allBlocked = false
                            },
                            label = { Text("Loop") },
                            leadingIcon = {
                                Icon(
                                    imageVector = if (isLooping) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                )
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                DiagonalWipeIconGridDemo(
                    animationMultiplier = globalAnimationMultiplier,
                    allIconsBlocked = allBlocked,
                    isLooping = isLooping,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun PaletteDot(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        shape = CircleShape,
        color = color,
        modifier = Modifier
            .size(36.dp)
            .border(
                BorderStroke(
                    if (isSelected) 2.5.dp else 1.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outlineVariant,
                ),
                shape = CircleShape,
            )
            .clip(CircleShape)
            .clickable(onClick = onClick),
    ) {
        if (isSelected) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}
