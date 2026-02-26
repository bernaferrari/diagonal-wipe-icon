This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Run Web (Chrome)

You can run the Compose web target directly from Chrome with:

```shell
./scripts/run-web-chrome.sh
```

The script starts `:composeApp:jsBrowserDevelopmentRun`, opens Chrome to the detected dev URL, and keeps the dev server running.
It discovers the actual dev-server URL from the Gradle logs, so if the port changes from 8080 (current default is 8081), it still opens the right page.

Alternative command (without auto-open):

```shell
./gradlew :composeApp:jsBrowserDevelopmentRun
```

Then open the printed dev URL from Gradle (for example, `http://localhost:8081`).

### Run in IntelliJ

If you prefer to run from IntelliJ, two run configurations are included:

- `Compose Web (Chrome)` calls `scripts/run-web-chrome.sh` and opens the detected dev URL automatically (typically `http://localhost:8081`).
- `Compose Web (Development)` runs `:composeApp:jsBrowserDevelopmentRun`.

Open **Run | Edit Configurations...** and select either configuration. If they don't appear, click **+** → **Gradle/Shell Script** and import from `.idea/runConfigurations`.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
