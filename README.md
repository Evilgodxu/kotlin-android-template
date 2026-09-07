<div align="center">

# Template

**A modern, out-of-the-box Android app template built with Jetpack Compose, MVVM and a modular architecture.**

**English** | [简体中文](README.zh-CN.md)

![License](https://img.shields.io/badge/license-MIT-green)
![Platform](https://img.shields.io/badge/platform-Android-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-2.4.20-purple)
![AGP](https://img.shields.io/badge/AGP-9.4.0-blue)
![Gradle](https://img.shields.io/badge/Gradle-9.7.0-blue)
![Compose BOM](https://img.shields.io/badge/Compose%20BOM-2026.08.00-blue)
![minSdk](https://img.shields.io/badge/minSdk-32-orange)
![targetSdk](https://img.shields.io/badge/targetSdk-37-orange)

</div>

**Template** is a production-ready starting point for new Android apps. The project skeleton and everyday plumbing are already wired up, so you can focus on business features instead of boilerplate.

## Features

- **Single-Activity architecture** with Jetpack Compose + Material 3, edge-to-edge rendering
- **MVVM with unidirectional data flow** — `UiState` + `ViewModel` per screen
- **Adaptive assemblies + semantic components** — screens are composed by `CompactAssembly` / `ExpandedAssembly` per window size class; page components live in `component/` with descriptive, suffix-free names
- **Navigation3** with typed routes and an explicit back stack
- **Koin** dependency injection, started in `Application.onCreate`
- **DataStore Preferences** persistence for app settings
- **Theme modes** — System / Light / Dark, with a circular reveal transition animation when switching
- **In-app language switching** — 简体中文 / English / Follow System, hot-swapped at runtime without recreating the Activity (per-language resource bundles disabled so switching always works)
- **Crash log manager** — uncaught and caught exceptions written to app-specific external storage, auto-cleaned after a retention period, chaining to the system default handler; today's log can be shared from the Settings page
- **Version update check** — the Settings page queries the latest GitHub release and prompts when a newer version is available
- **Optimized build setup** — R8 + resource shrinking for release, signed release build, `arm64-v8a`-only ABI filter, deterministic APK naming

## Screens

| Screen | Contents |
| --- | --- |
| Home | Welcome and project overview cards, entry to Settings |
| Settings | Appearance (theme), Language, and About (version, share today's crash log, check for updates, GitHub link) |

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Kotlin 2.4.20 |
| UI | Jetpack Compose (BOM 2026.08.00) + Material 3 |
| Navigation | AndroidX Navigation3 1.1.7 |
| DI | Koin 4.2.2 |
| Persistence | DataStore Preferences 1.2.1 |
| Serialization | kotlinx.serialization 1.11.0 |
| Lifecycle | androidx.lifecycle 2.11.0, activity-compose 1.13.0 |
| Build | AGP 9.4.0, Gradle 9.7.0, refreshVersions 0.60.6 |

## Project Structure

```
.
├── app/
│   └── src/main/
│       ├── kotlin/com/template/evilgodxu/
│       │   ├── data/                    # Data layer (DataStore)
│       │   │   ├── repository/          #   SettingsRepository
│       │   │   └── settings/            #   Settings state & keys
│       │   ├── di/                      # Koin modules
│       │   ├── log/                     # CrashLogManager
│       │   ├── navigation/              # Navigation3 typed routes
│       │   ├── screens/                 # Feature modules
│       │   │   ├── home/                #   Home feature
│       │   │   │   ├── compact/         #     Narrow-screen assembly
│       │   │   │   ├── expanded/        #     Wide-screen assembly
│       │   │   │   └── component/       #     Welcome / About cards
│       │   │   └── settings/            #   Settings feature
│       │   │       ├── compact/         #     Narrow-screen assembly
│       │   │       ├── expanded/        #     Wide-screen assembly
│       │   │       ├── component/       #     Appearance / Language / AppInfo
│       │   │       └── dialog/          #     Selection dialogs
│       │   ├── theme/                   # Material 3 color & typography
│       │   ├── ui/                      # Shared UI (topbar / section card / window size)
│       │   ├── update/                  # Latest-release check (GitHub API)
│       │   ├── utils/localization/      # In-app localization manager
│       │   ├── TemplateActivity.kt
│       │   └── TemplateApplication.kt
│       └── res/                         # Resources (values / values-en)
├── gradle/
│   ├── libs.versions.toml               # Version catalog (dependencies)
│   └── wrapper/
├── docs/                                # Architecture notes
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Architecture

The app follows **MVVM with unidirectional data flow**: state flows down from `ViewModel` → `UiState` → UI, while events flow up from the UI to the `ViewModel`. Shared data logic lives in the `data/` layer behind a repository, and everything is wired together by Koin.

Code is organized with a **modular pattern driven by window size classes**:

- `{ScreenName}Screen.kt` — screen entry, wires the ViewModel to the UI and dispatches by size class
- `{ScreenName}CompactAssembly.kt` / `{ScreenName}ExpandedAssembly.kt` — adaptive assemblies for narrow / wide windows
- `{Name}.kt` under `component/` — a self-contained UI block with a single semantic responsibility, named without generic suffixes

Shared code is promoted to the top level (`data/`, `theme/`, `utils/`) when reused by more than one feature; feature-specific code stays inside the feature module.

## Getting Started

### Prerequisites

- JDK 21
- Android Studio (latest stable recommended)
- Android SDK with API 37 (`compileSdk`)

### Build

```bash
git clone https://github.com/Evilgodxu/android-template.git
cd android-template

# Debug build
./gradlew assembleDebug

# Release build (requires signing config, see below)
./gradlew assembleRelease
```

APKs are emitted as `Template-<versionName>-arm64.apk` under `app/build/outputs/apk/`.

### Release Signing

The release build reads signing credentials from `local.properties` in the project root:

```properties
KEYSTORE_PASSWORD=your_store_password
KEY_ALIAS=jh
KEY_PASSWORD=your_key_password
```

The keystore file is expected at `jh.keystore` in the project root (adjust `storeFile` in `app/build.gradle.kts` if needed). `jh.keystore` and `local.properties` are git-ignored — never commit them.

## Customizing the Template

- **Rename the application / package**: update `namespace` and `applicationId` in `app/build.gradle.kts`, move the Kotlin sources under `app/src/main/kotlin/`, and update the manifest. Avoid a name collision with the existing `com.template.evilgodxu`.
- **App name**: edit `app_name` in `app/src/main/res/values/strings.xml`.
- **Theme colors**: edit `app/src/main/kotlin/.../theme/Color.kt`.
- **Supported ABIs**: adjust `ndk.abiFilters` in `app/build.gradle.kts` (currently `arm64-v8a`).
- **Add a new screen**: create a `screens/<name>/` feature module with its `UiState` + `ViewModel` + `Compact`/`Expanded` assemblies and semantic components under `component/`, register the route in `navigation/Screen.kt`, and add it to `AppNavHost`.

## License

[MIT](LICENSE) © 2026 Evilgodxu