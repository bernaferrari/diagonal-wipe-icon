package com.bernaferrari.diagonalwipeicon.demo

import com.bernaferrari.diagonalwipeicon.DiagonalWipeIcon
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIconDefaults
import com.bernaferrari.diagonalwipeicon.SlowAnimationMultiplier
import com.bernaferrari.diagonalwipeicon.WipeIconSource
import com.bernaferrari.diagonalwipeicon.autoPlayDelay

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun IconPreviewDialog(
    iconPair: MaterialWipeIconPair,
    baseAnimationMultiplier: Float,
    onDismiss: () -> Unit,
) {
    var blocked by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(true) }
    var pausedTapWiped by remember { mutableStateOf(false) }
    var hasHoveredWhilePaused by remember { mutableStateOf(false) }
    var previewSlowMode by remember { mutableStateOf(baseAnimationMultiplier > 1f) }
    val previewInteractionSource = remember { MutableInteractionSource() }
    val isPreviewHovered by previewInteractionSource.collectIsHoveredAsState()
    val codeSnippet = remember(iconPair) { buildDiagonalWipeUsageSnippet(iconPair) }

    val isSlow = previewSlowMode || baseAnimationMultiplier > 1f
    val previewIsWiped = when {
        isPlaying -> blocked
        hasHoveredWhilePaused -> isPreviewHovered
        else -> pausedTapWiped
    }

    val loopDelayMs = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeInDurationMillis,
        if (isSlow) SlowAnimationMultiplier else 1f
    )
    LaunchedEffect(isPlaying, loopDelayMs) {
        if (!isPlaying) return@LaunchedEffect
        while (true) {
            blocked = true
            delay(loopDelayMs.toLong())
            blocked = false
            delay(loopDelayMs.toLong())
        }
    }

    LaunchedEffect(isPlaying, isPreviewHovered) {
        if (!isPlaying && isPreviewHovered) {
            hasHoveredWhilePaused = true
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 0.dp,
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(0.94f),
        ) {
            BoxWithConstraints(
                modifier = Modifier.padding(32.dp),
            ) {
                val isWideLayout = maxWidth >= 780.dp
                if (isWideLayout) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        IconPreviewInteractivePane(
                            iconPair = iconPair,
                            isPlaying = isPlaying,
                            previewSlowMode = previewSlowMode,
                            previewIsWiped = previewIsWiped,
                            previewInteractionSource = previewInteractionSource,
                            onPreviewTap = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapWiped = false
                                    hasHoveredWhilePaused = false
                                } else {
                                    blocked = false
                                    hasHoveredWhilePaused = false
                                    pausedTapWiped = !pausedTapWiped
                                }
                            },
                            onTogglePlaying = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapWiped = false
                                    hasHoveredWhilePaused = false
                                } else {
                                    blocked = false
                                    isPlaying = true
                                }
                            },
                            onToggleSlow = { previewSlowMode = !previewSlowMode },
                            modifier = Modifier.weight(1f),
                        )
                        IconPreviewCodePane(
                            codeSnippet = codeSnippet,
                            modifier = Modifier.weight(1f),
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        IconPreviewInteractivePane(
                            iconPair = iconPair,
                            isPlaying = isPlaying,
                            previewSlowMode = previewSlowMode,
                            previewIsWiped = previewIsWiped,
                            previewInteractionSource = previewInteractionSource,
                            onPreviewTap = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapWiped = false
                                    hasHoveredWhilePaused = false
                                } else {
                                    blocked = false
                                    hasHoveredWhilePaused = false
                                    pausedTapWiped = !pausedTapWiped
                                }
                            },
                            onTogglePlaying = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapWiped = false
                                    hasHoveredWhilePaused = false
                                } else {
                                    blocked = false
                                    isPlaying = true
                                }
                            },
                            onToggleSlow = { previewSlowMode = !previewSlowMode },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        IconPreviewCodePane(
                            codeSnippet = codeSnippet,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IconPreviewInteractivePane(
    iconPair: MaterialWipeIconPair,
    isPlaying: Boolean,
    previewSlowMode: Boolean,
    previewIsWiped: Boolean,
    previewInteractionSource: MutableInteractionSource,
    onPreviewTap: () -> Unit,
    onTogglePlaying: () -> Unit,
    onToggleSlow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = materialWipeIconLabel(iconPair.label),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .hoverable(previewInteractionSource)
                .clickable(
                    interactionSource = previewInteractionSource,
                    indication = null,
                    onClick = onPreviewTap,
                ),
            contentAlignment = Alignment.Center,
        ) {
            val stiffness = if (previewSlowMode) Spring.StiffnessVeryLow else Spring.StiffnessLow
            DiagonalWipeIcon(
                isWiped = previewIsWiped,
                basePainter = iconPair.enabledIcon.painter(),
                wipedPainter = iconPair.disabledIcon.painter(),
                baseTint = MaterialTheme.colorScheme.primary,
                wipedTint = MaterialTheme.colorScheme.secondary,
                contentDescription = materialWipeIconLabel(iconPair.label),
                modifier = Modifier.size(120.dp),
                motion = DiagonalWipeIconDefaults.spring(
                    wipeInStiffness = stiffness,
                    wipeOutStiffness = stiffness,
                    wipeInDampingRatio = Spring.DampingRatioNoBouncy,
                    wipeOutDampingRatio = Spring.DampingRatioNoBouncy,
                ),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PreviewControlButton(
                selected = isPlaying,
                onClick = onTogglePlaying,
                label = if (isPlaying) "Playing" else "Paused",
                icon = if (isPlaying) MaterialSymbolIcons.Pause else MaterialSymbolIcons.PlayArrow,
            )
            PreviewControlButton(
                selected = previewSlowMode,
                onClick = onToggleSlow,
                label = if (previewSlowMode) "0.5x" else "1x",
                icon = MaterialSymbolIcons.Speed,
            )
        }
    }
}

@Composable
private fun PreviewControlButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: WipeIconSource,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            isHovered -> MaterialTheme.colorScheme.surfaceVariant
            else -> MaterialTheme.colorScheme.surface
        },
        tonalElevation = if (isHovered) 2.dp else 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .height(44.dp)
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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Icon(
                painter = icon.painter(),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun IconPreviewCodePane(
    codeSnippet: String,
    modifier: Modifier = Modifier,
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var didCopy by remember { mutableStateOf(false) }

    LaunchedEffect(didCopy) {
        if (didCopy) {
            delay(1400)
            didCopy = false
        }
    }

    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 0.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Code",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                val copyInteractionSource = remember { MutableInteractionSource() }
                val isCopyHovered by copyInteractionSource.collectIsHoveredAsState()
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isCopyHovered) MaterialTheme.colorScheme.surface else Color.Transparent,
                    modifier = Modifier
                        .size(36.dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                        .hoverable(copyInteractionSource)
                        .clickable(
                            interactionSource = copyInteractionSource,
                            indication = null,
                            onClick = {
                                scope.launch {
                                    clipboard.setClipEntry(ClipEntry.withPlainText(codeSnippet))
                                    didCopy = true
                                }
                            },
                        ),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = if (didCopy) {
                                MaterialSymbolIcons.Check.painter()
                            } else {
                                MaterialSymbolIcons.ContentCopy.painter()
                            },
                            contentDescription = if (didCopy) "Copied" else "Copy code",
                            modifier = Modifier.size(20.dp),
                            tint = if (didCopy) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 180.dp, max = 320.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                ) {
                    KotlinCodeText(codeSnippet)
                }
            }
        }
    }
}

@Composable
private fun KotlinCodeText(code: String) {
    val keywords = setOf("import", "var", "by", "remember", "mutableStateOf", "false", "true")
    val composableFunctions = setOf("DiagonalWipeIcon")
    val tokens = code.split(Regex("(?<=\\s)|(?=\\s)|(?<=[()=,])|(?=[()=,])"))

    val annotatedString = buildAnnotatedString {
        tokens.forEach { token ->
            when {
                token in keywords -> withStyle(SpanStyle(color = Color(0xFF0077AA))) { append(token) }
                token.startsWith("\"") && token.endsWith("\"") ->
                    withStyle(SpanStyle(color = Color(0xFF228822))) { append(token) }
                token in composableFunctions ->
                    withStyle(SpanStyle(color = Color(0xFFDD8822))) { append(token) }
                token.startsWith("MaterialTheme.") || token.startsWith("MaterialSymbolIcons.") || token.startsWith(
                    "Modifier."
                ) -> withStyle(SpanStyle(color = Color(0xFF8855AA))) { append(token) }
                else -> append(token)
            }
        }
    }

    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
        modifier = Modifier.fillMaxWidth(),
    )
}

private fun buildDiagonalWipeUsageSnippet(iconPair: MaterialWipeIconPair): String {
    val enabledName = iconPair.enabledCodeIconName ?: guessEnabledIconName(iconPair.label)
    val disabledName = iconPair.disabledCodeIconName ?: guessDisabledIconName(iconPair.label)
    val enabledResName = toDrawableResourceName(enabledName)
    val disabledResName = toDrawableResourceName(disabledName)

    return """
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Res
import org.jetbrains.compose.resources.painterResource
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIcon
import com.bernaferrari.diagonalwipeicon.DiagonalWipeIconDefaults

var isWiped by remember { mutableStateOf(false) }

val enabledPainter = painterResource(Res.drawable.$enabledResName)
val disabledPainter = painterResource(Res.drawable.$disabledResName)

DiagonalWipeIcon(
    isWiped = isWiped,
    basePainter = enabledPainter,
    wipedPainter = disabledPainter,
    baseTint = MaterialTheme.colorScheme.primary,
    wipedTint = MaterialTheme.colorScheme.secondary,
    contentDescription = "${materialWipeIconLabel(iconPair.label)}",
    modifier = Modifier.size(120.dp),
)
""".trimIndent()
}

private fun guessEnabledIconName(label: String): String {
    val base = when {
        label.startsWith("No ") -> label.removePrefix("No ")
        label.endsWith(" Off") -> label.removeSuffix(" Off")
        label.endsWith(" Disabled") -> label.removeSuffix(" Disabled")
        else -> label
    }
    return toIconSymbol(base)
}

private fun toDrawableResourceName(codeIconName: String): String =
    codeIconName
        .replace(Regex("(?<=[a-z0-9])(?=[A-Z])"), "_")
        .replace(Regex("(?<=[A-Z])(?=[A-Z][a-z])"), "_")
        .lowercase() + "_24px"

private fun guessDisabledIconName(label: String): String {
    return when {
        label.startsWith("No ") -> "No${toIconSymbol(label.removePrefix("No "))}"
        label.endsWith(" Off") -> "${toIconSymbol(label.removeSuffix(" Off"))}Off"
        label.endsWith(" Disabled") -> "${toIconSymbol(label.removeSuffix(" Disabled"))}Disabled"
        else -> "${toIconSymbol(label)}Disabled"
    }
}

private fun toIconSymbol(raw: String): String = raw
    .replace(Regex("[^A-Za-z0-9 ]"), " ")
    .split(Regex("\\s+"))
    .filter { it.isNotBlank() }
    .joinToString(separator = "") { word ->
        word.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
