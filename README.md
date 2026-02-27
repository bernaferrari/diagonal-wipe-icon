<div align="center">

# ✨ Diagonal Wipe Icon

**Smooth icon transitions for Compose Multiplatform**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.6+-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-green.svg?style=flat)](LICENSE)

<img src="assets/demo.gif" width="600" alt="Diagonal Wipe Demo">

**[Try Demo](https://github.com/bernaferrari/diagonal-wipe-icon)** • **[Copy File](composeApp/src/commonMain/kotlin/com/bernaferrari/DiagonalWipeIcon.kt)**

</div>

---

## The 30-Second Pitch

**Before:** Static icon swaps feel jarring ❌  
**After:** Smooth diagonal wipes feel polished ✨

```kotlin
// Before: Instant, jarring change
Icon(
    imageVector = if (isOn) Icons.Outlined.Power 
                  else Icons.Outlined.PowerOff
)

// After: Smooth, animated transition
DiagonalWipeIcon(
    isWiped = !isOn,
    baseIcon = Icons.Outlined.Power,
    wipedIcon = Icons.Outlined.PowerOff
)
```

**SF Symbols** has this. **Material Icons** doesn't. Until now.

---

## Why This Exists

**The Problem:**  
iOS developers get beautiful icon transitions for free with SF Symbols. Android/Compose developers? Either hand-write complex animations or live with instant cuts.

**The Solution:**  
One file. Copy. Paste. Done. Zero dependencies. Works with any icon.

---

## Installation

```bash
# Option 1: Copy the file
curl -O https://raw.githubusercontent.com/bernaferrari/diagonal-wipe-icon/main/composeApp/src/commonMain/kotlin/com/bernaferrari/DiagonalWipeIcon.kt

# Option 2: Copy-paste from GitHub
# File is at: composeApp/src/commonMain/kotlin/com/bernaferrari/DiagonalWipeIcon.kt
```

Drop it in your `commonMain/kotlin` folder. That's it. No Gradle. No dependencies. No version conflicts.

---

## Usage

### Basic Toggle Button

```kotlin
@Composable
fun PowerButton() {
    var isOn by remember { mutableStateOf(false) }
    
    IconButton(onClick = { isOn = !isOn }) {
        DiagonalWipeIcon(
            isWiped = !isOn,
            baseIcon = Icons.Outlined.Power,
            wipedIcon = Icons.Outlined.PowerOff
        )
    }
}
```

### With Custom Colors

```kotlin
DiagonalWipeIcon(
    isWiped = isMuted,
    baseIcon = Icons.Outlined.VolumeUp,
    wipedIcon = Icons.Outlined.VolumeOff,
    baseTint = MaterialTheme.colorScheme.primary,
    wipedTint = MaterialTheme.colorScheme.secondary,
    contentDescription = "Toggle mute"
)
```

### Canonical Recipes

```kotlin
// 1) Balanced default (great for most UI)
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeIconDefaults.gentle()
)

// 2) Fast/snappy interactions (switches, quick controls)
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeIconDefaults.snappy(
        direction = WipeDirection.LeftToRight
    )
)

// 3) Expressive/brand-heavy motion (more personality)
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeIconDefaults.expressive(
        direction = WipeDirection.BottomToTop
    )
)
```

### Custom Specs (Tween, Spring, Or Mixed)

```kotlin
DiagonalWipeIcon(
    ...,
    motion = DiagonalWipeMotion(
        direction = WipeDirection.LeftToRight,
        wipeInSpec = spring(stiffness = Spring.StiffnessMediumLow),
        wipeOutSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )
)
```

### Tween vs Spring

- Use `tween` when you need precise timing, consistency, and easy synchronization with other UI events.
- Use `spring` when you want more natural physics and tactile feel.
- Mix both when entering and exiting should feel different (e.g. fast reveal + softer reset).

---

## How It Works

```
Base Icon          Mask Layer          Result
    ⚡                ╱╲                ⚡
    ⚡      +        ╱  ╲      =       🔌
    ⚡              ╱    ╲             🔌
```

A moving diagonal mask reveals one icon while hiding the other. Both layers stay synchronized perfectly.

**Technical Details:**
- Canvas-based compositing (GPU-accelerated)
- Custom path clipping for 8 directions
- 530ms reveal / 800ms hide (customizable)
- Cubic-bezier easing for natural motion
- Fast paths at rest states (draws a single layer)
- During animation it does extra work vs a static vector (clipping + compositing)

---

## API Reference

```kotlin
@Composable
fun DiagonalWipeIcon(
    isWiped: Boolean,                    // Target state
    baseIcon: ImageVector,               // Icon shown when isWiped = false
    wipedIcon: ImageVector,              // Icon shown when isWiped = true
    baseTint: Color = Color.Unspecified, // Tint for base icon
    wipedTint: Color = Color.Unspecified,// Tint for wiped icon
    contentDescription: String? = null,  // Accessibility label
    modifier: Modifier = Modifier,       // Size, padding, etc.
    motion: DiagonalWipeMotion = DiagonalWipeIconDefaults.defaultMotion
)

@Immutable
data class DiagonalWipeMotion(
    val direction: WipeDirection = WipeDirection.TopLeftToBottomRight,
    val wipeInSpec: FiniteAnimationSpec<Float> = tween(...),  // tween / spring / keyframes ...
    val wipeOutSpec: FiniteAnimationSpec<Float> = tween(...), // tween / spring / keyframes ...
    val seamOverlapPx: Float = 0.8f
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

## Try It Yourself

```bash
# Desktop (macOS/Linux/Windows)
./gradlew :composeApp:run

# Web (Wasm)
./gradlew :composeApp:wasmJsBrowserRun

# Android
./gradlew :composeApp:installDebug
```

Play with 70+ icon pairs. Test all 8 wipe directions. See it in action.

---

## Performance

| Metric | What to expect |
|--------|----------------|
| At rest (`isWiped` settled) | Close to a normal single icon draw |
| During transition | Heavier than static vector draw (two layers + clipping) |
| Typical UX impact | Smooth for buttons/toggles; avoid animating many continuously |
| Binary size | Small (single Kotlin file, no extra dependency) |

---

## FAQ

**Q: Can I use custom icons?**  
A: Yes! Any `ImageVector` works — Material, FontAwesome, your own SVGs.

**Q: Does it work on iOS?**  
A: Yes, via Compose Multiplatform.

**Q: What about accessibility?**  
A: Full support. Just pass a `contentDescription`.

**Q: Can I change the animation speed?**  
A: Yes. Use `DiagonalWipeIconDefaults.tween(wipeInDurationMillis = ..., wipeOutDurationMillis = ...)`.

**Q: Can I use spring animations or custom easing?**  
A: Yes. Pass `DiagonalWipeMotion(wipeInSpec = spring(...), wipeOutSpec = tween(...))`.

---

## License

MIT © [Bernardo Ferrari](https://github.com/bernaferrari)

---

<div align="center">

Made with ☕ and Compose Multiplatform

</div>
