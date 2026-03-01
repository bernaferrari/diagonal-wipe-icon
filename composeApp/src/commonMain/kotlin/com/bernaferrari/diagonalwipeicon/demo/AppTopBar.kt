package com.bernaferrari.diagonalwipeicon.demo

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AnimatedTopBar(
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
                        painter = if (isDark) {
                            MaterialSymbolIcons.LightMode.painter()
                        } else {
                            MaterialSymbolIcons.DarkMode.painter()
                        },
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
