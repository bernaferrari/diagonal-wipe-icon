package com.bernaferrari.diagonalwipeicon.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun BottomToolbar(
    themeSeedOptions: List<ThemeSeed>,
    selectedSeedIndex: Int,
    onSeedSelected: (Int) -> Unit,
    isSlowMode: Boolean,
    onToggleSlowMode: () -> Unit,
    isLooping: Boolean,
    onToggleLoop: () -> Unit,
) {
    HorizontalFloatingToolbar(
        expanded = true,
        modifier = Modifier.padding(bottom = 16.dp),
        colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
            toolbarContainerColor = MaterialTheme.colorScheme.surface,
            toolbarContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RoundedCornerShape(20.dp),
        expandedShadowElevation = 4.dp,
        collapsedShadowElevation = 1.dp,
        leadingContent = {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                themeSeedOptions.forEachIndexed { index, seed ->
                    ThemeColorSwatch(
                        label = seed.name,
                        color = seed.color,
                        isSelected = index == selectedSeedIndex,
                        onClick = { onSeedSelected(index) }
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
        },
        trailingContent = {
            FilledTonalButton(
                onClick = onToggleLoop,
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isLooping) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.78f)
                    },
                    contentColor = if (isLooping) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                ),
            ) {
                Icon(
                    painter = if (isLooping) {
                        MaterialSymbolIcons.Stop.painter()
                    } else {
                        MaterialSymbolIcons.PlayArrow.painter()
                    },
                    contentDescription = null,
                    modifier = Modifier.size(17.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isLooping) "Stop" else "Loop",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            TextButton(
                onClick = onToggleSlowMode,
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (isSlowMode) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                ),
            ) {
                Icon(
                    painter = MaterialSymbolIcons.Speed.painter(),
                    contentDescription = null,
                    modifier = Modifier.size(17.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isSlowMode) "0.5x" else "1x",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = if (isSlowMode) FontWeight.SemiBold else FontWeight.Medium
                    ),
                )
            }
        }
    }
}
