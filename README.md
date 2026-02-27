<div align="center">

# Diagonal Wipe Icon

**Smooth icon transitions for Compose Multiplatform**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.6+-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-green.svg?style=flat)](LICENSE)

<img src="assets/screenshots/01-before-after.png" width="680" alt="Diagonal wipe icon before and after">

**[Run Demo Locally](#run-the-demo)** • **[Copy Single File](composeApp/src/commonMain/kotlin/com/bernaferrari/DiagonalWipeIcon.kt)**

</div>

---

## What This Is

`DiagonalWipeIcon` is a single-file Compose Multiplatform component that animates icon state changes with a diagonal (or axis-aligned) wipe.

It is designed for:
- Toggle-style controls (`on/off`, `enabled/disabled`, `mute/unmute`)
- Product UI where icon transitions should feel polished
- Teams that want zero extra library dependency and easy copy/paste adoption

---

## Why Use It

- Better visual continuity than instant icon swaps
- Supports **both** `ImageVector` and `Painter`
- Motion is fully configurable (`tween`, `spring`, mixed specs)
- Works in `commonMain`
- Small integration cost: one Kotlin file

---

## Installation

```bash
# Copy the file directly
curl -O https://raw.githubusercontent.com/bernaferrari/diagonal-wipe-icon/main/composeApp/src/commonMain/kotlin/com/bernaferrari/DiagonalWipeIcon.kt
```

Place it in your `commonMain/kotlin` source set.

No extra dependency is required for the component itself.

---

## Quick Start

```kotlin
@Composable
fun PowerButton() {
    var isOn by remember { mutableStateOf(false) }

    IconButton(onClick = { isOn = !isOn }) {
        DiagonalWipeIcon(
            isWiped = !isOn,
            baseIcon = Icons.Outlined.Power,
            wipedIcon = Icons.Outlined.PowerOff,
            contentDescription = "Toggle power"
        )
    }
}
```

---

## API Overview

### `ImageVector` overload

```kotlin
@Composable
fun DiagonalWipeIcon(
    isWiped: Boolean,
    baseIcon: ImageVector,
    wipedIcon: ImageVector,
    baseTint: Color = Color.Unspecified,
    wipedTint: Color = Color.Unspecified,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    motion: DiagonalWipeMotion = DiagonalWipeIconDefaults.defaultMotion
)
```

### `Painter` overload

```kotlin
@Composable
fun DiagonalWipeIcon(
    isWiped: Boolean,
    basePainter: Painter,
    wipedPainter: Painter,
    baseTint: Color = Color.Unspecified,
    wipedTint: Color = Color.Unspecified,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    motion: DiagonalWipeMotion = DiagonalWipeIconDefaults.defaultMotion
)
```

### Motion model

```kotlin
@Immutable
data class DiagonalWipeMotion(
    val direction: WipeDirection = WipeDirection.TopLeftToBottomRight,
    val wipeInSpec: FiniteAnimationSpec<Float>,
    val wipeOutSpec: FiniteAnimationSpec<Float>,
    val seamOverlapPx: Float = 0.8f,
)

object DiagonalWipeIconDefaults {
    fun gentle(...): DiagonalWipeMotion
    fun snappy(...): DiagonalWipeMotion
    fun expressive(...): DiagonalWipeMotion
    fun tween(...): DiagonalWipeMotion
    fun spring(...): DiagonalWipeMotion
}
```

---

## Motion Recipes

### Balanced default

```kotlin
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeIconDefaults.gentle()
)
```

### Fast control interaction

```kotlin
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeIconDefaults.snappy(
        direction = WipeDirection.LeftToRight
    )
)
```

### Custom spring/tween mix

```kotlin
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeMotion(
        direction = WipeDirection.BottomToTop,
        wipeInSpec = spring(stiffness = Spring.StiffnessMediumLow),
        wipeOutSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )
)
```

---

## How It Works

A moving boundary path reveals one icon while clipping the other.

- Layer A: base icon
- Layer B: wiped icon
- Shared reveal path controls both layers
- Supports 8 directions (diagonal + cardinal)

<img src="assets/screenshots/02-how-it-works.png" width="900" alt="How the diagonal wipe works">

---

## Performance

### Practical expectations

- At rest (`isWiped` settled): close to a normal single icon draw
- During transition: heavier than a static vector draw (two layers + clipping)
- Typical app usage: smooth for buttons/toggles
- Worst case to avoid: many icons animating continuously at once

In most real apps, that worst case is unlikely.

### Built-in optimizations

- Settled states use direct single-layer draw paths
- Tint color filters are cached and reused
- Wipe path/scratch buffers are reused across frames
- Direction math uses lightweight scalar calculations

---

## Production Guidance

- Excellent for prototypes and demos
- Also appropriate for production UI with occasional state transitions
- If you have high-density continuously animated surfaces, profile and reduce concurrent wipes

---

## Accessibility

- Pass `contentDescription` for semantic labeling
- Respect `prefers-reduced-motion` at app level if your product has strict motion policies

---

## Run The Demo

```bash
# Desktop
./gradlew :composeApp:run

# Web (Wasm)
./gradlew :composeApp:wasmJsBrowserRun

# Android
./gradlew :composeApp:installDebug
```

---

## FAQ

**Can I use custom icons?**  
Yes. Any `ImageVector` works, and you can also use any `Painter`.

**Can I use springs or custom easing?**  
Yes. Provide `wipeInSpec`/`wipeOutSpec` via `DiagonalWipeMotion`.

**Is this only for prototypes?**  
No. It is great for prototypes and generally production-safe for typical UI interactions.

---

## License

MIT © [Bernardo Ferrari](https://github.com/bernaferrari)
