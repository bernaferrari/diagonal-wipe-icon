package com.bernaferrari

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.collectIsPressedAsState

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.LabelOff
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.SpeakerNotes
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import kotlin.math.min
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue

data class MaterialWipeIconPair(
    val label: String,
    val enabledIcon: ImageVector,
    val disabledIcon: ImageVector,
)

data class MaterialWipeIconSection(
    val title: String,
    val subtitle: String,
    val icons: List<MaterialWipeIconPair>,
)

private val coreMaterialWipeIconCatalog = listOf(
    MaterialWipeIconPair("Alarm Off", Icons.Outlined.Alarm, Icons.Outlined.AlarmOff),
    MaterialWipeIconPair("Bedtime Off", Icons.Outlined.Bedtime, Icons.Outlined.BedtimeOff),
    MaterialWipeIconPair("Cloud Off", Icons.Outlined.Cloud, Icons.Outlined.CloudOff),
    MaterialWipeIconPair("Code Off", Icons.Outlined.Code, Icons.Outlined.CodeOff),
    MaterialWipeIconPair(
        "Credit Card Off",
        Icons.Outlined.CreditCard,
        Icons.Outlined.CreditCardOff
    ),
    MaterialWipeIconPair(
        "Developer Board Off",
        Icons.Outlined.DeveloperBoard,
        Icons.Outlined.DeveloperBoardOff
    ),
    MaterialWipeIconPair(
        "Do Not Disturb Off",
        Icons.Outlined.DoNotDisturb,
        Icons.Outlined.DoNotDisturbOff
    ),
    MaterialWipeIconPair("Explore Off", Icons.Outlined.Explore, Icons.Outlined.ExploreOff),
    MaterialWipeIconPair(
        "File Download Off",
        Icons.Outlined.FileDownload,
        Icons.Outlined.FileDownloadOff
    ),
    MaterialWipeIconPair("Filter Alt Off", Icons.Outlined.FilterAlt, Icons.Outlined.FilterAltOff),
    MaterialWipeIconPair(
        "Filter List Off",
        Icons.Outlined.FilterList,
        Icons.Outlined.FilterListOff
    ),
    MaterialWipeIconPair("Folder Off", Icons.Outlined.Folder, Icons.Outlined.FolderOff),
    MaterialWipeIconPair(
        "Font Download Off",
        Icons.Outlined.FontDownload,
        Icons.Outlined.FontDownloadOff
    ),
    MaterialWipeIconPair("Headset Off", Icons.Outlined.Headset, Icons.Outlined.HeadsetOff),
    MaterialWipeIconPair("Hls Off", Icons.Outlined.Hls, Icons.Outlined.HlsOff),
    MaterialWipeIconPair(
        "Invert Colors Off",
        Icons.Outlined.InvertColors,
        Icons.Outlined.InvertColorsOff
    ),
    MaterialWipeIconPair("Key Off", Icons.Outlined.Key, Icons.Outlined.KeyOff),
    MaterialWipeIconPair(
        "Label Off", Icons.AutoMirrored.Outlined.Label,
        Icons.AutoMirrored.Outlined.LabelOff
    ),
    MaterialWipeIconPair("Link Off", Icons.Outlined.Link, Icons.Outlined.LinkOff),
    MaterialWipeIconPair("Mic Off", Icons.Outlined.Mic, Icons.Outlined.MicOff),
    MaterialWipeIconPair(
        "Open In New Off",
        Icons.AutoMirrored.Outlined.OpenInNew, Icons.Outlined.OpenInNewOff
    ),
    MaterialWipeIconPair("Piano Off", Icons.Outlined.Piano, Icons.Outlined.PianoOff),
    MaterialWipeIconPair("Power Off", Icons.Outlined.Power, Icons.Outlined.PowerOff),
    MaterialWipeIconPair("Public Off", Icons.Outlined.Public, Icons.Outlined.PublicOff),
    MaterialWipeIconPair("Report Off", Icons.Outlined.Report, Icons.Outlined.ReportOff),
    MaterialWipeIconPair("Sensors Off", Icons.Outlined.Sensors, Icons.Outlined.SensorsOff),
    MaterialWipeIconPair(
        "Speaker Notes Off",
        Icons.AutoMirrored.Outlined.SpeakerNotes, Icons.Outlined.SpeakerNotesOff
    ),
    MaterialWipeIconPair("Subtitles Off", Icons.Outlined.Subtitles, Icons.Outlined.SubtitlesOff),
    MaterialWipeIconPair("Timer Off", Icons.Outlined.Timer, Icons.Outlined.TimerOff),
    MaterialWipeIconPair("Videocam Off", Icons.Outlined.Videocam, Icons.Outlined.VideocamOff),
    MaterialWipeIconPair("Visibility Off", Icons.Outlined.Visibility, Icons.Outlined.VisibilityOff),
    MaterialWipeIconPair("Wifi Off", Icons.Outlined.Wifi, Icons.Outlined.WifiOff),
    MaterialWipeIconPair(
        "Bluetooth Disabled",
        Icons.Outlined.Bluetooth,
        Icons.Outlined.BluetoothDisabled
    ),
    MaterialWipeIconPair(
        "Closed Caption Disabled",
        Icons.Outlined.ClosedCaption,
        Icons.Outlined.ClosedCaptionDisabled
    ),
    MaterialWipeIconPair("Domain Disabled", Icons.Outlined.Domain, Icons.Outlined.DomainDisabled),
    MaterialWipeIconPair("No Luggage", Icons.Outlined.Luggage, Icons.Outlined.NoLuggage),
    MaterialWipeIconPair("No Stroller", Icons.Outlined.Stroller, Icons.Outlined.NoStroller),
    MaterialWipeIconPair(
        "Person Add Disabled",
        Icons.Outlined.PersonAdd,
        Icons.Outlined.PersonAddDisabled
    ),
    MaterialWipeIconPair("Print Disabled", Icons.Outlined.Print, Icons.Outlined.PrintDisabled),
)

private val knownProblemsMaterialWipeIconCatalog = listOf(
    MaterialWipeIconPair("Group Off", Icons.Outlined.Group, Icons.Outlined.GroupOff),
    MaterialWipeIconPair(
        "Content Paste Off",
        Icons.Outlined.ContentPaste,
        Icons.Outlined.ContentPasteOff
    ),
    MaterialWipeIconPair("Directions Off", Icons.Outlined.Directions, Icons.Outlined.DirectionsOff),
    MaterialWipeIconPair("Extension Off", Icons.Outlined.Extension, Icons.Outlined.ExtensionOff),
    MaterialWipeIconPair("Hearing", Icons.Outlined.Hearing, Icons.Outlined.HearingDisabled),
    MaterialWipeIconPair("Near Me Disabled", Icons.Outlined.NearMe, Icons.Outlined.NearMeDisabled),
    MaterialWipeIconPair(
        "No Meeting Room",
        Icons.Outlined.MeetingRoom,
        Icons.Outlined.NoMeetingRoom
    ),
    MaterialWipeIconPair("Sync Disabled", Icons.Outlined.Sync, Icons.Outlined.SyncDisabled),
    MaterialWipeIconPair("Update Disabled", Icons.Outlined.Update, Icons.Outlined.UpdateDisabled),
    MaterialWipeIconPair("Usb Off", Icons.Outlined.Usb, Icons.Outlined.UsbOff),
    MaterialWipeIconPair("Tv Off", Icons.Outlined.Tv, Icons.Outlined.TvOff),
    MaterialWipeIconPair(
        "Videogame Asset Off",
        Icons.Outlined.VideogameAsset,
        Icons.Outlined.VideogameAssetOff
    ),
    MaterialWipeIconPair("Vpn Key Off", Icons.Outlined.VpnKey, Icons.Outlined.VpnKeyOff),
    MaterialWipeIconPair("Watch Off", Icons.Outlined.Watch, Icons.Outlined.WatchOff),
    MaterialWipeIconPair("Web Asset Off", Icons.Outlined.WebAsset, Icons.Outlined.WebAssetOff),
    MaterialWipeIconPair(
        "Wifi Tethering Off",
        Icons.Outlined.WifiTethering,
        Icons.Outlined.WifiTetheringOff
    ),
)

private val iconSections = listOf(
    MaterialWipeIconSection(
        title = "Ready to use",
        subtitle = "These icon pairs morph seamlessly",
        icons = coreMaterialWipeIconCatalog,
    ),
    MaterialWipeIconSection(
        title = "Needs refinement",
        subtitle = "There are some size and rotation differences.",
        icons = knownProblemsMaterialWipeIconCatalog,
    ),
)

private val howItWorksPair = coreMaterialWipeIconCatalog.first { it.label == "Power Off" }

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

internal val materialWipeIconCatalogSize = iconSections.sumOf { it.icons.size }

internal fun MaterialWipeIconLabel(rawLabel: String): String = rawLabel
    .removeSuffix(" Off")
    .removeSuffix(" Disabled")
    .removeSuffix(" off")
    .removeSuffix(" disabled")

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SectionWithStaggeredGrid(
    section: MaterialWipeIconSection,
    sectionIndex: Int,
    animationMultiplier: Float,
    allIconsWiped: Boolean,
    isLooping: Boolean,
    accentColor: Color,
    onIconSelected: (MaterialWipeIconPair) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Section header with dramatic typography
        MaterialWipeIconSectionHeaderV2(section, accentColor)

        // Staggered grid using FlowRow
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 6,
            overflow = FlowRowOverflow.Visible
        ) {
            section.icons.forEachIndexed { index, iconPair ->
                DiagonalWipeIconGridItemV2(
                    iconPair = iconPair,
                    animationMultiplier = animationMultiplier,
                    allIconsWiped = allIconsWiped,
                    isLooping = isLooping,
                    accentColor = accentColor,
                    onOpen = { onIconSelected(iconPair) }
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiagonalWipeIconGridDemo(
    modifier: Modifier = Modifier,
    animationMultiplier: Float = 1f,
    allIconsWiped: Boolean = false,
    isLooping: Boolean = false,
    accentColor: Color = Color(0xFF007FFF),
    externalSelectedIcon: MaterialWipeIconPair? = null,
    onExternalSelectedIconConsumed: () -> Unit = {},
) {
    var selectedIcon by remember { mutableStateOf<MaterialWipeIconPair?>(null) }

    // Handle external icon selection from hero
    LaunchedEffect(externalSelectedIcon) {
        externalSelectedIcon?.let {
            selectedIcon = it
            onExternalSelectedIconConsumed()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        iconSections.forEachIndexed { sectionIndex, section ->
            SectionWithStaggeredGrid(
                section = section,
                sectionIndex = sectionIndex,
                animationMultiplier = animationMultiplier,
                allIconsWiped = allIconsWiped,
                isLooping = isLooping,
                accentColor = accentColor,
                onIconSelected = { selectedIcon = it }
            )
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
private fun HowItWorksTeaserCard(
    animationMultiplier: Float,
    direction: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
    onOpenDialog: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    val arrowOffset by animateFloatAsState(
        targetValue = if (isHovered) 6f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isHovered) 2.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = if (isHovered) 2.dp else 0.dp,
        shadowElevation = 0.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        modifier = Modifier
            .widthIn(max = 600.dp)
            .padding(vertical = 8.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onOpenDialog,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Left: Text content
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "How it works",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Explore the animation",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Arrow
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer { translationX = arrowOffset },
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
internal fun HowItWorksDialog(
    animationMultiplier: Float,
    direction: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
    onDismiss: () -> Unit,
) {
    // Always use slow mode for "How it works" dialog so users can see the animation clearly
    val isWiped = rememberLoopingWipe(SlowAnimationMultiplier)
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
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "The first tiles show the two icon layers; then mask preview and final result.",
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
                    text = "One mask controls both icons, so they always stay in sync.",
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
    val maskBaseColor = allowedColor
    val maskRevealColor = blockedColor
    val enableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeInDurationMillis, animationMultiplier)
    val disableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeOutDurationMillis, animationMultiplier)
    val howItWorksMotion = DiagonalWipeIconDefaults.tween(
        direction = direction,
        wipeInDurationMillis = enableDuration,
        wipeOutDurationMillis = disableDuration,
    )
    val progress = rememberWipeProgress(isWiped = isWiped, motion = howItWorksMotion)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // First card: Base + Overlay + Mask
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

                // Plus
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

                // Plus
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

        // Equals sign
        Text(
            text = "=",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        // Final result with surface
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
                        basePainter = rememberVectorPainter(iconPair.enabledIcon),
                        wipedPainter = rememberVectorPainter(iconPair.disabledIcon),
                        baseTint = allowedColor,
                        wipedTint = blockedColor,
                        contentDescription = MaterialWipeIconLabel(iconPair.label),
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
            // Visual direction grid - centered
            DirectionArrowGrid(
                selectedDirection = direction,
                onDirectionChange = onDirectionChange,
            )

            // Direction label at bottom - plain text, no background
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clearAndSetSemantics { },
            )
        }
    }
}

@Composable
@Suppress("DEPRECATION")
private fun DirectionArrowGrid(
    selectedDirection: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
) {
    // Joystick/compass layout - arrows point outward from center
    // Like a D-pad: intuitive and fun
    Column(
        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand, overrideDescendants = true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Top row: ↖ ↑ ↗
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DirectionIconButton(
                icon = Icons.Outlined.NorthWest,
                rotation = 0f, // ↖ pointing up-left = FROM bottom-right TO top-left
                selected = selectedDirection == WipeDirection.BottomRightToTopLeft,
                onClick = { onDirectionChange(WipeDirection.BottomRightToTopLeft) },
            )
            DirectionIconButton(
                icon = Icons.Filled.KeyboardArrowUp,
                rotation = 0f, // ↑ pointing up = FROM bottom TO top
                selected = selectedDirection == WipeDirection.BottomToTop,
                onClick = { onDirectionChange(WipeDirection.BottomToTop) },
            )
            DirectionIconButton(
                icon = Icons.Outlined.NorthEast,
                rotation = 0f, // ↗ pointing up-right = FROM bottom-left TO top-right
                selected = selectedDirection == WipeDirection.BottomLeftToTopRight,
                onClick = { onDirectionChange(WipeDirection.BottomLeftToTopRight) },
            )
        }
        // Middle row: ← · →
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DirectionIconButton(
                icon = Icons.Filled.KeyboardArrowLeft,
                rotation = 0f, // ← pointing left = FROM right TO left
                selected = selectedDirection == WipeDirection.RightToLeft,
                onClick = { onDirectionChange(WipeDirection.RightToLeft) },
            )
            // Center spacer - no background
            Box(modifier = Modifier.size(44.dp))
            DirectionIconButton(
                icon = Icons.Filled.KeyboardArrowRight,
                rotation = 0f, // → pointing right = FROM left TO right
                selected = selectedDirection == WipeDirection.LeftToRight,
                onClick = { onDirectionChange(WipeDirection.LeftToRight) },
            )
        }
        // Bottom row: ↙ ↓ ↘
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DirectionIconButton(
                icon = Icons.Outlined.SouthWest,
                rotation = 0f, // ↙ pointing down-left = FROM top-right TO bottom-left
                selected = selectedDirection == WipeDirection.TopRightToBottomLeft,
                onClick = { onDirectionChange(WipeDirection.TopRightToBottomLeft) },
            )
            DirectionIconButton(
                icon = Icons.Filled.KeyboardArrowDown,
                rotation = 0f, // ↓ pointing down = FROM top TO bottom
                selected = selectedDirection == WipeDirection.TopToBottom,
                onClick = { onDirectionChange(WipeDirection.TopToBottom) },
            )
            DirectionIconButton(
                icon = Icons.Outlined.SouthEast,
                rotation = 0f, // ↘ pointing down-right = FROM top-left TO bottom-right
                selected = selectedDirection == WipeDirection.TopLeftToBottomRight,
                onClick = { onDirectionChange(WipeDirection.TopLeftToBottomRight) },
            )
        }
    }
}

@Composable
private fun DirectionIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    rotation: Float,
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
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        tonalElevation = when {
            selected -> 2.dp
            isHovered -> 1.dp
            else -> 0.dp
        },
        modifier = Modifier
            .size(44.dp)
            .zIndex(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            // Compose Web can surface text-cursor from descendants; force hand for this hit target.
            .pointerHoverIcon(PointerIcon.Hand, overrideDescendants = true)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer { rotationZ = rotation },
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
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
    icon: ImageVector,
    tint: Color,
    entersOnReveal: Boolean,
    motion: DiagonalWipeMotion,
    modifier: Modifier = Modifier,
) {
    val painter = rememberVectorPainter(icon)

    DiagonalWipeIconAtProgress(
        progress = progress,
        basePainter = painter,
        wipedPainter = painter,
        baseTint = if (entersOnReveal) Color.Transparent else tint,
        wipedTint = if (entersOnReveal) tint else Color.Transparent,
        contentDescription = null,
        motion = motion,
        modifier = modifier
            .padding(2.dp),
    )
}

@Composable
private fun OverlappingIconsPreview(
    baseIcon: ImageVector,
    overlayIcon: ImageVector,
    baseTint: Color,
    overlayTint: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        // Base icon - offset up-left, rotated slightly
        Icon(
            imageVector = baseIcon,
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .graphicsLayer {
                    translationX = -6f
                    translationY = -6f
                    rotationZ = -8f
                },
            tint = baseTint.copy(alpha = 0.7f),
        )
        // Overlay icon - offset down-right, rotated opposite
        Icon(
            imageVector = overlayIcon,
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .graphicsLayer {
                    translationX = 6f
                    translationY = 6f
                    rotationZ = 8f
                },
            tint = overlayTint.copy(alpha = 0.7f),
        )
    }
}


@Composable
private fun IconWipePreview(
    isWiped: Boolean,
    iconPair: MaterialWipeIconPair,
    allowedColor: Color,
    blockedColor: Color,
    direction: WipeDirection,
    modifier: Modifier = Modifier,
) {
    DiagonalWipeIcon(
        isWiped = isWiped,
        baseIcon = iconPair.enabledIcon,
        wipedIcon = iconPair.disabledIcon,
        baseTint = allowedColor,
        wipedTint = blockedColor,
        contentDescription = null,
        motion = DiagonalWipeIconDefaults.tween(direction = direction),
        modifier = modifier
            .padding(2.dp),
    )
}

@Composable
private fun MaskPreviewWithIcons(
    progress: Float,
    baseIcon: ImageVector,
    overlayIcon: ImageVector,
    baseTint: Color,
    overlayTint: Color,
    motion: DiagonalWipeMotion,
    modifier: Modifier = Modifier,
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    // Simple 3D-like flip showing A becoming B
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        // Rotation based on progress (0 to 180 degrees for flip effect)
        val rotation = clampedProgress * 180f

        // Base icon (A) - visible when rotation is 0-90
        if (rotation < 90f) {
            Icon(
                imageVector = baseIcon,
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

        // Overlay icon (B) - visible when rotation is 90-180
        if (rotation > 90f) {
            Icon(
                imageVector = overlayIcon,
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

        // Wipe line showing the transition
        if (clampedProgress in 0.2f..0.8f) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val (start, end) = buildWipeBoundaryLine(
                    width = size.width,
                    height = size.height,
                    progress = clampedProgress,
                    direction = motion.direction,
                ) ?: return@Canvas
                drawLine(
                    color = Color.White.copy(alpha = 0.8f),
                    start = start,
                    end = end,
                    strokeWidth = 2.dp.toPx(),
                )
            }
        }
    }
}

@Composable
private fun DiagonalWipeColorPreview(
    isWiped: Boolean,
    baseColor: Color,
    revealColor: Color,
    showBoundary: Boolean,
    direction: WipeDirection,
    modifier: Modifier = Modifier,
) {
    val revealPath = remember { Path() }
    val wipePathScratch = remember { WipePathScratch() }

    val transition = updateTransition(targetState = isWiped, label = "howItWorksWipe")
    val revealProgress by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = DiagonalWipeIconDefaults.WipeInDurationMillis,
                    easing = DiagonalWipeIconDefaults.WipeInEasing,
                )
            } else {
                tween(
                    durationMillis = DiagonalWipeIconDefaults.WipeOutDurationMillis,
                    easing = DiagonalWipeIconDefaults.WipeOutEasing,
                )
            }
        },
        label = "howItWorksRevealProgress",
    ) { isBlocked ->
        if (isBlocked) 1f else 0f
    }

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
    ) {
        val progress = revealProgress.coerceIn(0f, 1f)
        drawRect(baseColor)

        if (progress <= 0f) return@Canvas

        buildWipeRevealPath(
            path = revealPath,
            width = size.width,
            height = size.height,
            progress = progress,
            direction = direction,
            scratch = wipePathScratch,
        )
        clipPath(path = revealPath, clipOp = ClipOp.Intersect) {
            drawRect(revealColor)
        }

        if (showBoundary) {
            val (start, end) = buildWipeBoundaryLine(
                width = size.width,
                height = size.height,
                progress = progress,
                direction = direction,
            ) ?: return@Canvas
            drawLine(
                color = Color.Black.copy(alpha = 0.2f),
                start = start,
                end = end,
                strokeWidth = 4.dp.toPx(),
            )
            drawLine(
                color = Color.White.copy(alpha = 0.9f),
                start = start,
                end = end,
                strokeWidth = 2.dp.toPx(),
            )
        }
    }
}

@Composable
private fun rememberLoopingWipe(
    animationMultiplier: Float,
): Boolean {
    var blocked by remember { mutableStateOf(false) }
    val enableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeInDurationMillis,
        animationMultiplier,
    )
    val disableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeOutDurationMillis,
        animationMultiplier,
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

@Composable
private fun MaterialWipeIconSectionHeader(section: MaterialWipeIconSection) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = section.subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DiagonalWipeIconGridItem(
    iconPair: MaterialWipeIconPair,
    animationMultiplier: Float,
    allIconsWiped: Boolean,
    isLooping: Boolean,
    onOpen: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val enableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeInDurationMillis, animationMultiplier)
    val disableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeOutDurationMillis, animationMultiplier)

    // Press feedback animation with slower exit for smoother feel
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "cardScale",
    )

    // Animate tonal elevation for smooth enter/exit
    val tonalElevation by animateDpAsState(
        targetValue = when {
            isPressed -> 2.dp
            isHovered -> 1.dp
            else -> 0.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow, // Slower, more graceful
        ),
        label = "cardElevation",
    )

    // Clean card: no shadow, uses tonal elevation only
    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = tonalElevation,
        shadowElevation = 0.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .size(170.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onOpen,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Icon container with animated background
            val backgroundAlpha by animateFloatAsState(
                targetValue = when {
                    isPressed -> 0.6f
                    isHovered -> 0.5f
                    else -> 0.4f
                },
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = "iconBackgroundAlpha",
            )
            val backgroundColor by animateColorAsState(
                targetValue = when {
                    isPressed -> MaterialTheme.colorScheme.primaryContainer
                    isHovered -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = "iconBackgroundColor",
            )
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundColor.copy(alpha = backgroundAlpha)),
                contentAlignment = Alignment.Center,
            ) {
                DiagonalWipeIcon(
                    isWiped = if (isLooping) allIconsWiped else isHovered,
                    baseIcon = iconPair.enabledIcon,
                    wipedIcon = iconPair.disabledIcon,
                    baseTint = MaterialTheme.colorScheme.primary,
                    wipedTint = MaterialTheme.colorScheme.secondary,
                    contentDescription = MaterialWipeIconLabel(iconPair.label),
                    modifier = Modifier.size(44.dp),
                    motion = DiagonalWipeIconDefaults.tween(
                        wipeInDurationMillis = enableDuration,
                        wipeOutDurationMillis = disableDuration,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = MaterialWipeIconLabel(iconPair.label),
                style = MaterialTheme.typography.labelLarge,
                color = when {
                    isPressed -> MaterialTheme.colorScheme.primary
                    isHovered -> MaterialTheme.colorScheme.onSurface
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun IconPreviewDialog(
    iconPair: MaterialWipeIconPair,
    baseAnimationMultiplier: Float,
    onDismiss: () -> Unit,
) {
    var blocked by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(true) }
    var pausedTapWiped by remember { mutableStateOf(false) }
    var hasHoveredWhilePaused by remember { mutableStateOf(false) }
    var previewSlowMode by remember { mutableStateOf(false) }
    val previewMultiplier = if (previewSlowMode) SlowAnimationMultiplier else 1f
    val effectiveMultiplier = baseAnimationMultiplier * previewMultiplier
    val previewInteractionSource = remember { MutableInteractionSource() }
    val isPreviewHovered by previewInteractionSource.collectIsHoveredAsState()
    val codeSnippet = remember(iconPair) { buildDiagonalWipeUsageSnippet(iconPair) }
    val enableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeInDurationMillis, effectiveMultiplier)
    val disableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeOutDurationMillis, effectiveMultiplier)
    val playbackEnableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeInDurationMillis,
        effectiveMultiplier,
    )
    val playbackDisableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.WipeOutDurationMillis,
        effectiveMultiplier,
    )
    val previewIsWiped = when {
        isPlaying -> blocked
        hasHoveredWhilePaused -> isPreviewHovered
        else -> pausedTapWiped
    }

    LaunchedEffect(isPlaying, playbackEnableDelay, playbackDisableDelay) {
        if (!isPlaying) return@LaunchedEffect
        while (true) {
            blocked = true
            delay(playbackEnableDelay.toLong())
            blocked = false
            delay(playbackDisableDelay.toLong())
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
                            enableDuration = enableDuration,
                            disableDuration = disableDuration,
                            onPreviewTap = {
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
                            enableDuration = enableDuration,
                            disableDuration = disableDuration,
                            onPreviewTap = {
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
    enableDuration: Int,
    disableDuration: Int,
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
            text = MaterialWipeIconLabel(iconPair.label),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Clean preview area with subtle background
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
            DiagonalWipeIcon(
                isWiped = previewIsWiped,
                baseIcon = iconPair.enabledIcon,
                wipedIcon = iconPair.disabledIcon,
                baseTint = MaterialTheme.colorScheme.primary,
                wipedTint = MaterialTheme.colorScheme.secondary,
                contentDescription = MaterialWipeIconLabel(iconPair.label),
                modifier = Modifier.size(120.dp),
                motion = DiagonalWipeIconDefaults.tween(
                    wipeInDurationMillis = enableDuration,
                    wipeOutDurationMillis = disableDuration,
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
                icon = if (isPlaying) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
            )
            PreviewControlButton(
                selected = previewSlowMode,
                onClick = onToggleSlow,
                label = if (previewSlowMode) "0.5x" else "1x",
                icon = Icons.Outlined.Speed,
            )
        }
    }
}

@Composable
private fun PreviewControlButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: ImageVector,
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
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            )
        }
    }
}

@Composable
@Suppress("DEPRECATION")
private fun IconPreviewCodePane(
    codeSnippet: String,
    modifier: Modifier = Modifier,
) {
    val clipboardManager = LocalClipboardManager.current
    var didCopy by remember { mutableStateOf(false) }

    LaunchedEffect(didCopy) {
        if (didCopy) {
            delay(1400)
            didCopy = false
        }
    }

    // Clean SAAS style
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
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                // Subtle icon-only copy button
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
                                clipboardManager.setText(AnnotatedString(codeSnippet))
                                didCopy = true
                            },
                        ),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (didCopy) Icons.Outlined.Check else Icons.Outlined.ContentCopy,
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
                // Simple syntax highlighted code block
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

    // Simple tokenizer
    val tokens = code.split(Regex("(?<=\\s)|(?=\\s)|(?<=[()=,])|(?=[()=,])"))

    val annotatedString = buildAnnotatedString {
        tokens.forEach { token ->
            when {
                token in keywords -> {
                    withStyle(SpanStyle(color = Color(0xFF0077AA))) {
                        append(token)
                    }
                }

                token.startsWith("\"") && token.endsWith("\"") -> {
                    withStyle(SpanStyle(color = Color(0xFF228822))) {
                        append(token)
                    }
                }

                token in composableFunctions -> {
                    withStyle(SpanStyle(color = Color(0xFFDD8822))) {
                        append(token)
                    }
                }

                token.startsWith("Icons.") || token.startsWith("MaterialTheme.") || token.startsWith(
                    "Modifier."
                ) -> {
                    withStyle(SpanStyle(color = Color(0xFF8855AA))) {
                        append(token)
                    }
                }

                else -> {
                    append(token)
                }
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
    val enabledName = guessEnabledIconName(iconPair.label)
    val disabledName = guessDisabledIconName(iconPair.label)
    val autoMirroredEnabledLabels = setOf("Label Off", "Open In New Off", "Speaker Notes Off")
    val enabledIconExpr = if (iconPair.label in autoMirroredEnabledLabels) {
        "Icons.AutoMirrored.Outlined.$enabledName"
    } else {
        "Icons.Outlined.$enabledName"
    }
    val disabledIconExpr = if (iconPair.label == "Label Off") {
        "Icons.AutoMirrored.Outlined.$disabledName"
    } else {
        "Icons.Outlined.$disabledName"
    }

    return """
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

var isWiped by remember { mutableStateOf(false) }

DiagonalWipeIcon(
    isWiped = isWiped,
    baseIcon = $enabledIconExpr,
    wipedIcon = $disabledIconExpr,
    baseTint = MaterialTheme.colorScheme.primary,
    wipedTint = MaterialTheme.colorScheme.secondary,
    contentDescription = "${MaterialWipeIconLabel(iconPair.label)}",
    modifier = Modifier.size(120.dp),
    // Optional: customize timing/direction or use springs.
    // motion = DiagonalWipeIconDefaults.tween(
    //     direction = WipeDirection.LeftToRight,
    //     wipeInDurationMillis = 280,
    //     wipeOutDurationMillis = 420,
    // ),
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

// ============================================
// NEW V2 COMPONENTS WITH GLASSMORPHISM & GLOW
// ============================================

@Composable
internal fun MaterialWipeIconSectionHeaderV2(
    section: MaterialWipeIconSection,
    accentColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
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
internal fun DiagonalWipeIconGridItemV2(
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

    val enableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeInDurationMillis, animationMultiplier)
    val disableDuration =
        scaledDuration(DiagonalWipeIconDefaults.WipeOutDurationMillis, animationMultiplier)

    // Subtle scale animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else if (isHovered) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    // Background color - simpler, no heavy glassmorphism
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isPressed -> MaterialTheme.colorScheme.surfaceVariant
            isHovered -> MaterialTheme.colorScheme.surface
            else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        },
        animationSpec = tween(150),
        label = "cardBackground"
    )

    // Border color - always present but animates from subtle to accent
    val borderColor by animateColorAsState(
        targetValue = when {
            isPressed -> accentColor
            isHovered -> accentColor.copy(alpha = 0.7f)
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        },
        animationSpec = tween(150),
        label = "borderColor"
    )

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(170.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onOpen,
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon container - simpler, no heavy gradient
            val iconBgColor by animateColorAsState(
                targetValue = when {
                    isPressed -> accentColor.copy(alpha = 0.2f)
                    isHovered -> accentColor.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                },
                animationSpec = tween(150),
                label = "iconBgColor"
            )

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                DiagonalWipeIcon(
                    isWiped = if (isLooping) allIconsWiped else isHovered,
                    baseIcon = iconPair.enabledIcon,
                    wipedIcon = iconPair.disabledIcon,
                    baseTint = MaterialTheme.colorScheme.primary,
                    wipedTint = MaterialTheme.colorScheme.secondary,
                    contentDescription = MaterialWipeIconLabel(iconPair.label),
                    modifier = Modifier.size(40.dp),
                    motion = DiagonalWipeIconDefaults.tween(
                        wipeInDurationMillis = enableDuration,
                        wipeOutDurationMillis = disableDuration,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Label - always visible, just changes weight
            Text(
                text = MaterialWipeIconLabel(iconPair.label),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (isHovered) FontWeight.SemiBold else FontWeight.Medium
                ),
                color = when {
                    isPressed -> accentColor
                    isHovered -> MaterialTheme.colorScheme.onSurface
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}
