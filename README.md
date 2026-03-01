<div align="center">

[<img src="assets/animated-icons.webp" width="680" alt="Diagonal wipe icon animation showing smooth transition between on/off states">](https://bernaferrari.github.io/diagonal-wipe-icon/)

# Diagonal Wipe Icon

**One-file icon transition component for Compose Multiplatform**


[<img src="assets/preview.png" width="680" alt="Diagonal wipe icon animation showing smooth transition between on/off states">](https://bernaferrari.github.io/diagonal-wipe-icon/)

**[🚀 Live Demo](https://bernaferrari.github.io/diagonal-wipe-icon/)**

</div>

---

## What Is This?

Apple's SF Symbols makes it easy to add wipe icon transitions to iOS apps. Compose lacks this functionality built-in into icons, and the expected behavior is too complicated (manually get two drawable icons, make an animated drawable, get the animation right by hand or using a third party editor, every time). Therefore, I made a component that emulates the same behavior using two icons and a mask. Easy for experimentation, easy for prototypes, also simple enough for production.

Perfect for:
- Toggle controls (`on/off`, `enabled/disabled`, `visible/hidden`)
- Settings screens with stateful icons
- Anywhere you want polished micro-interactions

**Zero dependencies. Copy a single file.**

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
A: Yes, but profile if you have high-density animated surfaces. On the demo, having hundreds of icons animating at once started being a problem, so a combination of LazyColumn and a few tricks mostly solved it.

**Q: Is this production-ready?**  
A: Yes. The implementation is straightforward and includes common tests for motion/config behavior; still, feel free to profile in your app context. If it is good enough and fast enough, great, if it is not you won't loose much time by trying it. This is a low-risk high-reward solution.

---

## 📝 License

MIT © [Bernardo Ferrari](https://github.com/bernaferrari)
