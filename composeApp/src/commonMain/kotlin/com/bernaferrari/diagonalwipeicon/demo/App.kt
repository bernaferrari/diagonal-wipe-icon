package com.bernaferrari.diagonalwipeicon.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIconDefaults
import com.bernaferrari.diagonalwipeicon.SlowAnimationMultiplier
import com.bernaferrari.diagonalwipeicon.WipeDirection
import com.bernaferrari.diagonalwipeicon.autoPlayDelay
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.dynamiccolor.ColorSpec
import kotlinx.coroutines.delay

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
    var scrollProgress by remember { mutableFloatStateOf(0f) }

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
    val titleAlpha = scrollProgress.coerceIn(0f, 1f)

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
            AnimatedMeshBackground(selectedSeed.color, isDark)

            Scaffold(
                topBar = {
                    AnimatedTopBar(
                        titleAlpha = titleAlpha,
                        isDark = isDark,
                        onToggleDark = { isDark = !isDark },
                        onOpenGitHub = { uriHandler.openUri("https://github.com/bernaferrari/diagonal-wipe-icon") },
                    )
                },
                containerColor = Color.Transparent
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    DiagonalWipeIconGridDemo(
                        animationMultiplier = globalAnimationMultiplier,
                        allIconsWiped = isGloballyWiped,
                        isLooping = isLooping,
                        accentColor = selectedSeed.color,
                        externalSelectedIcon = heroSelectedIcon,
                        onExternalSelectedIconConsumed = { heroSelectedIcon = null },
                        headerContent = {
                            AnimatedVisibility(
                                visible = showIntro,
                                enter = fadeIn(tween(500, easing = EaseOut)) +
                                        slideInVertically(tween(500, easing = EaseOut)) { it / 4 }
                            ) {
                                HeroSection(
                                    selectedSeed = selectedSeed,
                                    onOpenHowItWorks = { showHowItWorks = true },
                                    onIconClick = { iconPair -> heroSelectedIcon = iconPair }
                                )
                            }
                        },
                        onScrollProgressChanged = { progress -> scrollProgress = progress },
                        modifier = Modifier.fillMaxSize()
                    )

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
                                globalAnimationMultiplier =
                                    if (isSlowMode) 1f else SlowAnimationMultiplier
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
