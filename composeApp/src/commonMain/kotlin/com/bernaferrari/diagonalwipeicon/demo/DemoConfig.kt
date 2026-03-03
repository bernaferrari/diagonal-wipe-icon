package com.bernaferrari.diagonalwipeicon.demo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle

internal object UiTokens {
    val PageHorizontalPadding = 24.dp
    val GridMaxContentWidth = 1000.dp
    val GridItemSlotWidth = 136.dp

    const val ScrollProgressOffsetPx = 300f
    const val ScrollProgressQuantizationSteps = 60f

    val HeroIconSize = 90.dp
    val HeroIconSpacing = 20.dp
}

internal data class ThemeSeed(
    val name: String,
    val color: Color,
    val style: PaletteStyle,
)

internal val themeSeedOptions = listOf(
    ThemeSeed("Salmon", Color(0xFFFF8B7B), PaletteStyle.Expressive),
    ThemeSeed("Gold", Color(0xFFFFD700), PaletteStyle.Expressive),
    ThemeSeed("Mint", Color(0xFF00FA9A), PaletteStyle.Expressive),
    ThemeSeed("Azure", Color(0xFF007FFF), PaletteStyle.Expressive),
    ThemeSeed("Slate", Color(0xFF708090), PaletteStyle.Neutral),
)
