package com.bernaferrari.diagonalwipeicon

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.bernaferrari.diagonalwipeicon.demo.App
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(
        configure = {
            // Workaround: avoid web a11y shadow overlay cursor hit-test glitches on custom canvas controls.
            isA11YEnabled = false
        },
    ) { App() }

    installWebCursorFallback()
}

private fun installWebCursorFallback() {
    window.setInterval(
        handler = {
            val host = document.getElementById("root") as? HTMLElement ?: return@setInterval
            val canvas = host.shadowRoot?.querySelector("canvas") as? HTMLCanvasElement
                ?: return@setInterval
            if (canvas.style.cursor == "text") {
                canvas.style.cursor = "pointer"
            }
        },
        timeout = 16,
    )
}
