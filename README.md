<div align="center">

# Diagonal Wipe Icon

**One-file icon transition component for Compose Multiplatform**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.6+-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-green.svg?style=flat)](LICENSE)

<img src="assets/screenshots/01-before-after.png" width="680" alt="Diagonal wipe icon animation showing smooth transition between on/off states">

**[🚀 Live Demo](https://bernaferrari.github.io/diagonal-wipe-icon/)** • **[📄 Copy Single File](composeApp/src/commonMain/kotlin/com/bernaferrari/diagonalwipeicon/DiagonalWipeIcon.kt)** • **[🎨 Try Locally](#run-the-demo)**

</div>

---

## What Is This?

`DiagonalWipeIcon` is a **single Kotlin file** (~600 lines) that adds cinematic icon transitions to your Compose UI. Instead of jarring instant swaps, icons wipe between states with a smooth diagonal or directional mask.

Perfect for:
- Toggle controls (`on/off`, `enabled/disabled`, `visible/hidden`)
- Settings screens with stateful icons
- Anywhere you want polished micro-interactions

**Zero dependencies. Copy one file. Ship it.**

---

## ✨ Why Use It

| Feature | Benefit |
|---------|---------|
| 🎬 **Cinematic transitions** | Visual continuity between icon states |
| 📱 **Multiplatform-ready** | Android, iOS, and Web (JS with Wasm compatibility distribution) |
| 🎨 **Fully customizable** | 8 directions, springs, tweens, custom easing |
| 📦 **Zero bloat** | Single file, ~600 lines, no dependencies |
| 🏎️ **Performance-optimized** | Settled state = single icon draw |

---

## 🚀 Installation

Copy the file directly:

```bash
curl -O https://raw.githubusercontent.com/bernaferrari/diagonal-wipe-icon/main/composeApp/src/commonMain/kotlin/com/bernaferrari/diagonalwipeicon/DiagonalWipeIcon.kt
```

Drop it in your `commonMain/kotlin` folder. Done.

---

## 🎯 Quick Start

```kotlin
@Composable
fun FavoriteButton() {
    var isFavorite by remember { mutableStateOf(false) }

    IconButton(onClick = { isFavorite = !isFavorite }) {
        DiagonalWipeIcon(
            isWiped = !isFavorite,
            baseIcon = Icons.Outlined.Favorite,
            wipedIcon = Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite"
        )
    }
}
```

---

## 📚 API Overview

### ImageVector overload (most common)

```kotlin
@Composable
fun DiagonalWipeIcon(
    isWiped: Boolean,
    baseIcon: ImageVector,      // Shown when isWiped = false
    wipedIcon: ImageVector,      // Shown when isWiped = true
    baseTint: Color = Color.Unspecified,
    wipedTint: Color = Color.Unspecified,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    motion: DiagonalWipeMotion = DiagonalWipeIconDefaults.gentle()
)
```

### Painter overload (for custom assets)

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
    motion: DiagonalWipeMotion = DiagonalWipeIconDefaults.gentle()
)
```

---

## 🎨 Motion Presets

### Gentle (default)
Smooth, balanced feel for most toggles.

```kotlin
DiagonalWipeIcon(
    isWiped = isMuted,
    baseIcon = Icons.Outlined.VolumeUp,
    wipedIcon = Icons.Outlined.VolumeOff,
    motion = DiagonalWipeIconDefaults.gentle()
)
```

### Snappy
Fast response for controls that need to feel immediate.

```kotlin
DiagonalWipeIcon(
    isWiped = isPlaying,
    baseIcon = Icons.Outlined.PlayArrow,
    wipedIcon = Icons.Outlined.Pause,
    motion = DiagonalWipeIconDefaults.snappy()
)
```

### Custom direction
8 directions: 4 diagonals + 4 cardinals.

```kotlin
DiagonalWipeIcon(
    isWiped = isExpanded,
    baseIcon = Icons.Outlined.ExpandLess,
    wipedIcon = Icons.Outlined.ExpandMore,
    motion = DiagonalWipeIconDefaults.gentle(
        direction = WipeDirection.TopToBottom
    )
)
```

### Spring physics
For organic, physics-based motion.

```kotlin
DiagonalWipeIcon(
    isWiped = isChecked,
    baseIcon = Icons.Outlined.CheckBox,
    wipedIcon = Icons.Outlined.CheckBoxOutlineBlank,
    motion = DiagonalWipeIconDefaults.spring(
        wipeInStiffness = Spring.StiffnessLow,
        wipeOutStiffness = Spring.StiffnessLow
    )
)
```

### Fully custom
Mix springs, tweens, and easing.

```kotlin
DiagonalWipeIcon(
    isWiped = isLocked,
    baseIcon = Icons.Outlined.LockOpen,
    wipedIcon = Icons.Outlined.Lock,
    motion = DiagonalWipeMotion(
        direction = WipeDirection.LeftToRight,
        wipeInSpec = tween(400, easing = FastOutSlowInEasing),
        wipeOutSpec = spring(stiffness = Spring.StiffnessMedium)
    )
)
```

---

## 🔧 How It Works

A moving clip path reveals one icon while concealing the other:

1. **Base layer** (`baseIcon`) starts fully visible
2. **Overlay layer** (`wipedIcon`) starts fully hidden
3. **Shared reveal path** controls both layers simultaneously
4. **8 wipe directions** supported (4 diagonal + 4 cardinal)

<img src="assets/screenshots/02-how-it-works.png" width="900" alt="Diagram showing base icon + overlay icon + mask = final result">

---

## ⚡ Performance

### What to expect

| Scenario | Performance |
|----------|-------------|
| At rest (`isWiped` settled) | Same as single static icon |
| During transition | ~2x cost (two layers + clip path) |
| 5-10 toggles animating | Smooth on modern devices |
| 50+ icons animating continuously | Profile and optimize |

### Built-in optimizations

- ✅ Settled states bypass compositing entirely
- ✅ Tint filters cached and reused
- ✅ Path buffers pooled across frames
- ✅ Fast scalar math for boundary calculations

---

## 🎯 Production Checklist

- [x] Works with Material icons out of the box
- [x] Supports custom vector assets
- [x] No additional dependencies
- [x] Respects `contentDescription` for accessibility
- [ ] Consider an in-app reduced-motion toggle for motion-sensitive users

---

## Run the Demo

Try it in your browser:

**[https://bernaferrari.github.io/diagonal-wipe-icon/](https://bernaferrari.github.io/diagonal-wipe-icon/)**

Or run locally:

```bash
# Web (dev server)
./gradlew :composeApp:jsBrowserDevelopmentRun

# Android
./gradlew :androidApp:installDebug
```

---

## ❓ FAQ

**Q: Can I use my own icons?**  
A: Yes. Any `ImageVector` from Material Icons, or any `Painter` for custom assets.

**Q: Does it work on iOS?**  
A: Yes. Compose Multiplatform supports iOS, and this component works in `commonMain`.

**Q: What about accessibility?**  
A: Pass `contentDescription` and it just works. The component respects semantics.

**Q: Can I animate multiple icons at once?**  
A: Yes, but profile if you have high-density animated surfaces.

**Q: Is this production-ready?**  
A: Yes. The implementation is straightforward and includes common tests for motion/config behavior; still profile in your app context.

---

## 📝 License

MIT © [Bernardo Ferrari](https://github.com/bernaferrari)
