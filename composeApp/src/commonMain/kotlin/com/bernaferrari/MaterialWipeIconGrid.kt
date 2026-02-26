package com.bernaferrari

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue

private data class MaterialWipeIconPair(
    val label: String,
    val enabledIcon: ImageVector,
    val disabledIcon: ImageVector,
)

private data class MaterialWipeIconSection(
    val title: String,
    val subtitle: String,
    val icons: List<MaterialWipeIconPair>,
)

private val coreMaterialWipeIconCatalog = listOf(
    MaterialWipeIconPair("Alarm Off", Icons.Outlined.Alarm, Icons.Outlined.AlarmOff),
    MaterialWipeIconPair("Bedtime Off", Icons.Outlined.Bedtime, Icons.Outlined.BedtimeOff),
    MaterialWipeIconPair("Cloud Off", Icons.Outlined.Cloud, Icons.Outlined.CloudOff),
    MaterialWipeIconPair("Code Off", Icons.Outlined.Code, Icons.Outlined.CodeOff),
    MaterialWipeIconPair("Credit Card Off", Icons.Outlined.CreditCard, Icons.Outlined.CreditCardOff),
    MaterialWipeIconPair("Developer Board Off", Icons.Outlined.DeveloperBoard, Icons.Outlined.DeveloperBoardOff),
    MaterialWipeIconPair("Do Not Disturb Off", Icons.Outlined.DoNotDisturb, Icons.Outlined.DoNotDisturbOff),
    MaterialWipeIconPair("Explore Off", Icons.Outlined.Explore, Icons.Outlined.ExploreOff),
    MaterialWipeIconPair("File Download Off", Icons.Outlined.FileDownload, Icons.Outlined.FileDownloadOff),
    MaterialWipeIconPair("Filter Alt Off", Icons.Outlined.FilterAlt, Icons.Outlined.FilterAltOff),
    MaterialWipeIconPair("Filter List Off", Icons.Outlined.FilterList, Icons.Outlined.FilterListOff),
    MaterialWipeIconPair("Folder Off", Icons.Outlined.Folder, Icons.Outlined.FolderOff),
    MaterialWipeIconPair("Font Download Off", Icons.Outlined.FontDownload, Icons.Outlined.FontDownloadOff),
    MaterialWipeIconPair("Group Off", Icons.Outlined.Group, Icons.Outlined.GroupOff),
    MaterialWipeIconPair("Headset Off", Icons.Outlined.Headset, Icons.Outlined.HeadsetOff),
    MaterialWipeIconPair("Hls Off", Icons.Outlined.Hls, Icons.Outlined.HlsOff),
    MaterialWipeIconPair("Invert Colors Off", Icons.Outlined.InvertColors, Icons.Outlined.InvertColorsOff),
    MaterialWipeIconPair("Key Off", Icons.Outlined.Key, Icons.Outlined.KeyOff),
    MaterialWipeIconPair("Label Off", Icons.AutoMirrored.Outlined.Label,
        Icons.AutoMirrored.Outlined.LabelOff
    ),
    MaterialWipeIconPair("Link Off", Icons.Outlined.Link, Icons.Outlined.LinkOff),
    MaterialWipeIconPair("Mic Off", Icons.Outlined.Mic, Icons.Outlined.MicOff),
    MaterialWipeIconPair("Open In New Off",
        Icons.AutoMirrored.Outlined.OpenInNew, Icons.Outlined.OpenInNewOff),
    MaterialWipeIconPair("Piano Off", Icons.Outlined.Piano, Icons.Outlined.PianoOff),
    MaterialWipeIconPair("Power Off", Icons.Outlined.Power, Icons.Outlined.PowerOff),
    MaterialWipeIconPair("Public Off", Icons.Outlined.Public, Icons.Outlined.PublicOff),
    MaterialWipeIconPair("Report Off", Icons.Outlined.Report, Icons.Outlined.ReportOff),
    MaterialWipeIconPair("Sensors Off", Icons.Outlined.Sensors, Icons.Outlined.SensorsOff),
    MaterialWipeIconPair("Speaker Notes Off",
        Icons.AutoMirrored.Outlined.SpeakerNotes, Icons.Outlined.SpeakerNotesOff),
    MaterialWipeIconPair("Subtitles Off", Icons.Outlined.Subtitles, Icons.Outlined.SubtitlesOff),
    MaterialWipeIconPair("Timer Off", Icons.Outlined.Timer, Icons.Outlined.TimerOff),
    MaterialWipeIconPair("Videocam Off", Icons.Outlined.Videocam, Icons.Outlined.VideocamOff),
    MaterialWipeIconPair("Visibility Off", Icons.Outlined.Visibility, Icons.Outlined.VisibilityOff),
    MaterialWipeIconPair("Wifi Off", Icons.Outlined.Wifi, Icons.Outlined.WifiOff),
    MaterialWipeIconPair("Bluetooth Disabled", Icons.Outlined.Bluetooth, Icons.Outlined.BluetoothDisabled),
    MaterialWipeIconPair("Closed Caption Disabled", Icons.Outlined.ClosedCaption, Icons.Outlined.ClosedCaptionDisabled),
    MaterialWipeIconPair("Domain Disabled", Icons.Outlined.Domain, Icons.Outlined.DomainDisabled),
    MaterialWipeIconPair("No Luggage", Icons.Outlined.Luggage, Icons.Outlined.NoLuggage),
    MaterialWipeIconPair("No Stroller", Icons.Outlined.Stroller, Icons.Outlined.NoStroller),
    MaterialWipeIconPair("Person Add Disabled", Icons.Outlined.PersonAdd, Icons.Outlined.PersonAddDisabled),
    MaterialWipeIconPair("Print Disabled", Icons.Outlined.Print, Icons.Outlined.PrintDisabled),
)

private val knownProblemsMaterialWipeIconCatalog = listOf(
    MaterialWipeIconPair("Content Paste Off", Icons.Outlined.ContentPaste, Icons.Outlined.ContentPasteOff),
    MaterialWipeIconPair("Directions Off", Icons.Outlined.Directions, Icons.Outlined.DirectionsOff),
    MaterialWipeIconPair("Extension Off", Icons.Outlined.Extension, Icons.Outlined.ExtensionOff),
    MaterialWipeIconPair("Hearing", Icons.Outlined.Hearing, Icons.Outlined.HearingDisabled),
    MaterialWipeIconPair("Near Me Disabled", Icons.Outlined.NearMe, Icons.Outlined.NearMeDisabled),
    MaterialWipeIconPair("No Meeting Room", Icons.Outlined.MeetingRoom, Icons.Outlined.NoMeetingRoom),
    MaterialWipeIconPair("Sync Disabled", Icons.Outlined.Sync, Icons.Outlined.SyncDisabled),
    MaterialWipeIconPair("Update Disabled", Icons.Outlined.Update, Icons.Outlined.UpdateDisabled),
    MaterialWipeIconPair("Usb Off", Icons.Outlined.Usb, Icons.Outlined.UsbOff),
    MaterialWipeIconPair("Tv Off", Icons.Outlined.Tv, Icons.Outlined.TvOff),
    MaterialWipeIconPair("Videogame Asset Off", Icons.Outlined.VideogameAsset, Icons.Outlined.VideogameAssetOff),
    MaterialWipeIconPair("Vpn Key Off", Icons.Outlined.VpnKey, Icons.Outlined.VpnKeyOff),
    MaterialWipeIconPair("Watch Off", Icons.Outlined.Watch, Icons.Outlined.WatchOff),
    MaterialWipeIconPair("Web Asset Off", Icons.Outlined.WebAsset, Icons.Outlined.WebAssetOff),
    MaterialWipeIconPair("Wifi Tethering Off", Icons.Outlined.WifiTethering, Icons.Outlined.WifiTetheringOff),
)

private val iconSections = listOf(
    MaterialWipeIconSection(
        title = "Core icon transitions",
        subtitle = "These samples currently animate reliably.",
        icons = coreMaterialWipeIconCatalog,
    ),
    MaterialWipeIconSection(
        title = "Imperfect transitions",
        subtitle = "Some are imperfect due to icon-size/viewport, likely fixable by hand; others are very different and need custom transition logic.",
        icons = knownProblemsMaterialWipeIconCatalog,
    ),
)

private val howItWorksPair = coreMaterialWipeIconCatalog.first { it.label == "Power Off" }
private data class WipeDirectionOption(
    val direction: WipeDirection,
    val label: String,
)

private enum class DirectionPoint(val row: Int, val col: Int) {
    TopLeft(0, 0),
    TopCenter(0, 1),
    TopRight(0, 2),
    CenterLeft(1, 0),
    CenterRight(1, 2),
    BottomLeft(2, 0),
    BottomCenter(2, 1),
    BottomRight(2, 2),
}

private data class DirectionEndpoints(
    val from: DirectionPoint,
    val to: DirectionPoint,
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

private val howItWorksDirectionLabels = howItWorksDirectionOptions.associate { it.direction to it.label }

internal val materialWipeIconCatalogSize = iconSections.sumOf { it.icons.size }

private fun MaterialWipeIconLabel(rawLabel: String): String = rawLabel
    .removeSuffix(" Off")
    .removeSuffix(" Disabled")
    .removeSuffix(" off")
    .removeSuffix(" disabled")


@Composable
fun DiagonalWipeIconGridDemo(
    modifier: Modifier = Modifier,
    animationMultiplier: Float = 1f,
    allIconsBlocked: Boolean = false,
    isLooping: Boolean = false,
) {
    var selectedIcon by remember { mutableStateOf<MaterialWipeIconPair?>(null) }
    var showHowItWorksDialog by remember { mutableStateOf(false) }
    var howItWorksDirection by remember { mutableStateOf(WipeDirection.TopLeftToBottomRight) }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        state = rememberLazyGridState(),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            HowItWorksTeaserCard(
                animationMultiplier = animationMultiplier,
                direction = howItWorksDirection,
                onDirectionChange = { howItWorksDirection = it },
                onOpenDialog = { showHowItWorksDialog = true },
            )
        }
        iconSections.forEach { section ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                MaterialWipeIconSectionHeader(section)
            }
            items(section.icons) { iconPair ->
                DiagonalWipeIconGridItem(
                    iconPair = iconPair,
                    animationMultiplier = animationMultiplier,
                    allIconsBlocked = allIconsBlocked,
                    isLooping = isLooping,
                    onOpen = { selectedIcon = iconPair },
                )
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

    if (showHowItWorksDialog) {
        HowItWorksDialog(
            animationMultiplier = animationMultiplier,
            direction = howItWorksDirection,
            onDirectionChange = { howItWorksDirection = it },
            onDismiss = { showHowItWorksDialog = false },
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
    val blocked = rememberLoopingBlocked(animationMultiplier)
    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                        ),
                    ),
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "How it works",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "A mask moves across two icon layers.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                TextButton(onClick = onOpenDialog) {
                    Text("Explore")
                }
            }
            HowItWorksDirectionSelector(
                direction = direction,
                onDirectionChange = onDirectionChange,
            )
            HowItWorksFlow(
                iconPair = howItWorksPair,
                blocked = blocked,
                direction = direction,
                tileHeight = 98.dp,
                squareSize = 48.dp,
            )
        }
    }
}

@Composable
private fun HowItWorksDialog(
    animationMultiplier: Float,
    direction: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
    onDismiss: () -> Unit,
) {
    val blocked = rememberLoopingBlocked(animationMultiplier)
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 4.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(0.94f),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Text(
                    text = "How the wipe works",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "The first tiles show the two icon layers; then mask preview and final result.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                HowItWorksDirectionSelector(
                    direction = direction,
                    onDirectionChange = onDirectionChange,
                )
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                ) {
                    Box(modifier = Modifier.padding(12.dp)) {
                        HowItWorksFlow(
                            iconPair = howItWorksPair,
                            blocked = blocked,
                            direction = direction,
                            tileHeight = 116.dp,
                            squareSize = 58.dp,
                        )
                    }
                }
                Text(
                    text = "The same moving boundary clips both layers, so reveal/hide stays perfectly synchronized.",
                    style = MaterialTheme.typography.bodySmall,
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
    blocked: Boolean,
    direction: WipeDirection,
    tileHeight: Dp,
    squareSize: Dp,
) {
    val allowedColor = MaterialTheme.colorScheme.primary
    val blockedColor = MaterialTheme.colorScheme.error
    val maskBaseColor = MaterialTheme.colorScheme.primary
    val maskRevealColor = MaterialTheme.colorScheme.secondary

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        HowItWorksStep(
            title = "Base layer",
            subtitle = "Layer A exits",
            accentColor = allowedColor,
            frameHeight = tileHeight,
            modifier = Modifier.weight(1f),
        ) {
            SingleIconWipeLayerPreview(
                blocked = blocked,
                icon = iconPair.enabledIcon,
                tint = allowedColor,
                entersOnReveal = false,
                direction = direction,
                modifier = Modifier.size(squareSize),
            )
        }
        HowItWorksStep(
            title = "Overlay layer",
            subtitle = "Layer B enters",
            accentColor = blockedColor,
            frameHeight = tileHeight,
            modifier = Modifier.weight(1f),
        ) {
            SingleIconWipeLayerPreview(
                blocked = blocked,
                icon = iconPair.disabledIcon,
                tint = blockedColor,
                entersOnReveal = true,
                direction = direction,
                modifier = Modifier.size(squareSize),
            )
        }
        HowItWorksStep(
            title = "Mask preview",
            subtitle = "Boundary sweep",
            accentColor = maskRevealColor,
            frameHeight = tileHeight,
            modifier = Modifier.weight(1f),
        ) {
            DiagonalWipeColorPreview(
                blocked = blocked,
                baseColor = maskBaseColor,
                revealColor = maskRevealColor,
                showBoundary = true,
                direction = direction,
                modifier = Modifier.size(squareSize),
            )
        }
        HowItWorksStep(
            title = "Final result",
            subtitle = "Animated icon",
            accentColor = blockedColor,
            frameHeight = tileHeight,
            modifier = Modifier.weight(1f),
        ) {
            IconWipePreview(
                blocked = blocked,
                iconPair = iconPair,
                allowedColor = allowedColor,
                blockedColor = blockedColor,
                direction = direction,
                modifier = Modifier.size(squareSize),
            )
        }
    }
}

@Composable
private fun HowItWorksDirectionSelector(
    direction: WipeDirection,
    onDirectionChange: (WipeDirection) -> Unit,
) {
    val endpoints = directionEndpoints(direction)
    val label = howItWorksDirectionLabels[direction] ?: "Top-left to bottom-right"

    Surface(
        shape = RoundedCornerShape(14.dp),
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                DirectionPointGrid(
                    selected = endpoints.from,
                    onPointClick = { onDirectionChange(directionForFromPoint(it)) },
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "To",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                DirectionPointGrid(
                    selected = endpoints.to,
                    onPointClick = { onDirectionChange(directionForToPoint(it)) },
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "Wipe direction",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun DirectionPointGrid(
    selected: DirectionPoint,
    onPointClick: (DirectionPoint) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (row in 0..2) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (col in 0..2) {
                    val point = pointAt(row, col)
                    if (point == null) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(7.dp))
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(MaterialTheme.colorScheme.outlineVariant),
                            )
                        }
                    } else {
                        val isSelected = point == selected
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(7.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                    } else {
                                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.9f)
                                    },
                                    shape = RoundedCornerShape(7.dp),
                                )
                                .background(
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    },
                                )
                                .clickable { onPointClick(point) },
                            contentAlignment = Alignment.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(if (isSelected) 8.dp else 6.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        if (isSelected) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.outline
                                        },
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HowItWorksStep(
    title: String,
    subtitle: String,
    accentColor: Color,
    frameHeight: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            tonalElevation = 1.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .height(frameHeight)
                .border(
                    width = 1.dp,
                    color = accentColor.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(14.dp),
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.12f),
                                MaterialTheme.colorScheme.surface,
                            ),
                        ),
                    )
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

@Composable
private fun SingleIconWipeLayerPreview(
    blocked: Boolean,
    icon: ImageVector,
    tint: Color,
    entersOnReveal: Boolean,
    direction: WipeDirection,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, tint.copy(alpha = 0.45f), RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        tint.copy(alpha = 0.18f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        DiagonalWipeIcon(
            blocked = blocked,
            allowedIcon = icon,
            blockedIcon = icon,
            allowedTint = if (entersOnReveal) Color.Transparent else tint,
            blockedTint = if (entersOnReveal) tint else Color.Transparent,
            contentDescription = null,
            direction = direction,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        )
    }
}

@Composable
private fun IconWipePreview(
    blocked: Boolean,
    iconPair: MaterialWipeIconPair,
    allowedColor: Color,
    blockedColor: Color,
    direction: WipeDirection,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, blockedColor.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        allowedColor.copy(alpha = 0.15f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        DiagonalWipeIcon(
            blocked = blocked,
            allowedIcon = iconPair.enabledIcon,
            blockedIcon = iconPair.disabledIcon,
            allowedTint = allowedColor,
            blockedTint = blockedColor,
            contentDescription = null,
            direction = direction,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        )
    }
}

@Composable
private fun DiagonalWipeColorPreview(
    blocked: Boolean,
    baseColor: Color,
    revealColor: Color,
    showBoundary: Boolean,
    direction: WipeDirection,
    modifier: Modifier = Modifier,
) {
    val transition = updateTransition(targetState = blocked, label = "howItWorksWipe")
    val revealProgress by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = DiagonalWipeIconDefaults.EnableDurationMillis,
                    easing = DiagonalWipeIconDefaults.EnableEasing,
                )
            } else {
                tween(
                    durationMillis = DiagonalWipeIconDefaults.DisableDurationMillis,
                    easing = DiagonalWipeIconDefaults.DisableEasing,
                )
            }
        },
        label = "howItWorksRevealProgress",
    ) { isBlocked ->
        if (isBlocked) 1f else 0f
    }

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, revealColor.copy(alpha = 0.35f), RoundedCornerShape(12.dp)),
    ) {
        val progress = revealProgress.coerceIn(0f, 1f)
        drawRect(baseColor)

        if (progress <= 0f) return@Canvas

        val revealPath = buildWipeRevealPath(
            width = size.width,
            height = size.height,
            progress = progress,
            direction = direction,
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

private fun pointAt(row: Int, col: Int): DirectionPoint? {
    return DirectionPoint.entries.firstOrNull { it.row == row && it.col == col }
}

private fun directionEndpoints(direction: WipeDirection): DirectionEndpoints {
    return when (direction) {
        WipeDirection.TopLeftToBottomRight ->
            DirectionEndpoints(DirectionPoint.TopLeft, DirectionPoint.BottomRight)
        WipeDirection.BottomRightToTopLeft ->
            DirectionEndpoints(DirectionPoint.BottomRight, DirectionPoint.TopLeft)
        WipeDirection.TopRightToBottomLeft ->
            DirectionEndpoints(DirectionPoint.TopRight, DirectionPoint.BottomLeft)
        WipeDirection.BottomLeftToTopRight ->
            DirectionEndpoints(DirectionPoint.BottomLeft, DirectionPoint.TopRight)
        WipeDirection.TopToBottom ->
            DirectionEndpoints(DirectionPoint.TopCenter, DirectionPoint.BottomCenter)
        WipeDirection.BottomToTop ->
            DirectionEndpoints(DirectionPoint.BottomCenter, DirectionPoint.TopCenter)
        WipeDirection.LeftToRight ->
            DirectionEndpoints(DirectionPoint.CenterLeft, DirectionPoint.CenterRight)
        WipeDirection.RightToLeft ->
            DirectionEndpoints(DirectionPoint.CenterRight, DirectionPoint.CenterLeft)
    }
}

private fun directionForFromPoint(point: DirectionPoint): WipeDirection {
    return when (point) {
        DirectionPoint.TopLeft -> WipeDirection.TopLeftToBottomRight
        DirectionPoint.TopCenter -> WipeDirection.TopToBottom
        DirectionPoint.TopRight -> WipeDirection.TopRightToBottomLeft
        DirectionPoint.CenterLeft -> WipeDirection.LeftToRight
        DirectionPoint.CenterRight -> WipeDirection.RightToLeft
        DirectionPoint.BottomLeft -> WipeDirection.BottomLeftToTopRight
        DirectionPoint.BottomCenter -> WipeDirection.BottomToTop
        DirectionPoint.BottomRight -> WipeDirection.BottomRightToTopLeft
    }
}

private fun directionForToPoint(point: DirectionPoint): WipeDirection {
    return when (point) {
        DirectionPoint.TopLeft -> WipeDirection.BottomRightToTopLeft
        DirectionPoint.TopCenter -> WipeDirection.BottomToTop
        DirectionPoint.TopRight -> WipeDirection.BottomLeftToTopRight
        DirectionPoint.CenterLeft -> WipeDirection.RightToLeft
        DirectionPoint.CenterRight -> WipeDirection.LeftToRight
        DirectionPoint.BottomLeft -> WipeDirection.TopRightToBottomLeft
        DirectionPoint.BottomCenter -> WipeDirection.TopToBottom
        DirectionPoint.BottomRight -> WipeDirection.TopLeftToBottomRight
    }
}

@Composable
private fun rememberLoopingBlocked(
    animationMultiplier: Float,
): Boolean {
    var blocked by remember { mutableStateOf(false) }
    val enableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.EnableDurationMillis,
        animationMultiplier,
    )
    val disableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.DisableDurationMillis,
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
            .padding(horizontal = 4.dp, vertical = 6.dp),
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = section.subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DiagonalWipeIconGridItem(
    iconPair: MaterialWipeIconPair,
    animationMultiplier: Float,
    allIconsBlocked: Boolean,
    isLooping: Boolean,
    onOpen: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val enableDuration = scaledDuration(DiagonalWipeIconDefaults.EnableDurationMillis, animationMultiplier)
    val disableDuration = scaledDuration(DiagonalWipeIconDefaults.DisableDurationMillis, animationMultiplier)
    val cardAccentColor = if (isHovered) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }
    val cardTitleColor = if (isHovered) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        shape = RoundedCornerShape(14.dp),
        tonalElevation = 4.dp,
        modifier = Modifier
            .size(150.dp)
            .border(
                width = 1.dp,
                color = cardAccentColor.copy(alpha = if (isHovered) 0.35f else 0.24f),
                shape = RoundedCornerShape(14.dp),
            )
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onOpen,
            ),
        shadowElevation = if (isHovered) 6.dp else 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            if (isHovered) {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.58f)
                            },
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
                        ),
                    ),
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(
                        width = 1.dp,
                        color = cardAccentColor.copy(alpha = if (isHovered) 0.42f else 0.28f),
                        shape = RoundedCornerShape(14.dp),
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                if (isHovered) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
                                } else {
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                                },
                                MaterialTheme.colorScheme.surface,
                            ),
                        ),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                DiagonalWipeIcon(
                    blocked = if (isLooping) allIconsBlocked else isHovered,
                    allowedIcon = iconPair.enabledIcon,
                    blockedIcon = iconPair.disabledIcon,
                    allowedTint = MaterialTheme.colorScheme.primary,
                    blockedTint = MaterialTheme.colorScheme.error,
                    contentDescription = MaterialWipeIconLabel(iconPair.label),
                    modifier = Modifier.size(48.dp),
                    enableDurationMillis = enableDuration,
                    disableDurationMillis = disableDuration,
                )
            }
            Text(
                text = MaterialWipeIconLabel(iconPair.label),
                style = MaterialTheme.typography.labelSmall,
                color = cardTitleColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 11.dp)
                    .fillMaxWidth(),
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
    var pausedTapBlocked by remember { mutableStateOf(false) }
    var hasHoveredWhilePaused by remember { mutableStateOf(false) }
    var previewSlowMode by remember { mutableStateOf(false) }
    val previewMultiplier = if (previewSlowMode) SlowAnimationMultiplier else 1f
    val effectiveMultiplier = baseAnimationMultiplier * previewMultiplier
    val previewInteractionSource = remember { MutableInteractionSource() }
    val isPreviewHovered by previewInteractionSource.collectIsHoveredAsState()
    val codeSnippet = remember(iconPair) { buildDiagonalWipeUsageSnippet(iconPair) }
    val enableDuration = scaledDuration(DiagonalWipeIconDefaults.EnableDurationMillis, effectiveMultiplier)
    val disableDuration = scaledDuration(DiagonalWipeIconDefaults.DisableDurationMillis, effectiveMultiplier)
    val playbackEnableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.EnableDurationMillis,
        effectiveMultiplier,
    )
    val playbackDisableDelay = autoPlayDelay(
        DiagonalWipeIconDefaults.DisableDurationMillis,
        effectiveMultiplier,
    )
    val previewBlocked = when {
        isPlaying -> blocked
        hasHoveredWhilePaused -> isPreviewHovered
        else -> pausedTapBlocked
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
            shape = RoundedCornerShape(32.dp),
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(0.94f),
        ) {
            BoxWithConstraints(
                modifier = Modifier.padding(24.dp),
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
                            previewBlocked = previewBlocked,
                            previewInteractionSource = previewInteractionSource,
                            enableDuration = enableDuration,
                            disableDuration = disableDuration,
                            onPreviewTap = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapBlocked = false
                                    hasHoveredWhilePaused = false
                                } else if (!hasHoveredWhilePaused) {
                                    pausedTapBlocked = !pausedTapBlocked
                                }
                            },
                            onTogglePlaying = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapBlocked = false
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
                            previewBlocked = previewBlocked,
                            previewInteractionSource = previewInteractionSource,
                            enableDuration = enableDuration,
                            disableDuration = disableDuration,
                            onPreviewTap = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapBlocked = false
                                    hasHoveredWhilePaused = false
                                } else if (!hasHoveredWhilePaused) {
                                    pausedTapBlocked = !pausedTapBlocked
                                }
                            },
                            onTogglePlaying = {
                                if (isPlaying) {
                                    isPlaying = false
                                    blocked = false
                                    pausedTapBlocked = false
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
    previewBlocked: Boolean,
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
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .size(220.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .hoverable(previewInteractionSource)
                .clickable(
                    interactionSource = previewInteractionSource,
                    indication = null,
                    onClick = onPreviewTap,
                ),
            contentAlignment = Alignment.Center,
        ) {
            DiagonalWipeIcon(
                blocked = previewBlocked,
                allowedIcon = iconPair.enabledIcon,
                blockedIcon = iconPair.disabledIcon,
                allowedTint = MaterialTheme.colorScheme.primary,
                blockedTint = MaterialTheme.colorScheme.error,
                contentDescription = MaterialWipeIconLabel(iconPair.label),
                modifier = Modifier.size(120.dp),
                enableDurationMillis = enableDuration,
                disableDurationMillis = disableDuration,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = isPlaying,
                onClick = onTogglePlaying,
                label = { Text(if (isPlaying) "Playing" else "Paused") },
                leadingIcon = {
                    Icon(
                        imageVector = if (isPlaying) Icons.Outlined.Pause
                        else Icons.Outlined.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                },
            )
            FilterChip(
                selected = previewSlowMode,
                onClick = onToggleSlow,
                label = {
                    Icon(
                        imageVector = Icons.Outlined.SlowMotionVideo,
                        contentDescription = "Slow mode",
                        modifier = Modifier.size(18.dp),
                    )
                },
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

    Surface(
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Code",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                TextButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(codeSnippet))
                        didCopy = true
                    },
                ) {
                    Icon(
                        imageVector = if (didCopy) Icons.Outlined.Check else Icons.Outlined.ContentCopy,
                        contentDescription = if (didCopy) "Copied" else "Copy code",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(if (didCopy) "Copied" else "Copy")
                }
            }
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Text(
                    text = codeSnippet,
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 172.dp, max = 320.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(12.dp),
                )
            }
        }
    }
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

var blocked by remember { mutableStateOf(false) }

DiagonalWipeIcon(
    blocked = blocked,
    allowedIcon = $enabledIconExpr,
    blockedIcon = $disabledIconExpr,
    allowedTint = MaterialTheme.colorScheme.primary,
    blockedTint = MaterialTheme.colorScheme.error,
    contentDescription = "${MaterialWipeIconLabel(iconPair.label)}",
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
        word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
