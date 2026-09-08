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
![minSdk](https://img.shields.io/badge/minSdk-33-orange)
![targetSdk](https://img.shields.io/badge/targetSdk-37-orange)

</div>

**Template** is a production-ready starting point for new Android apps. The project skeleton and everyday plumbing are already wired up, so you can focus on business features instead of boilerplate.

## Features

- **Single-Activity architecture** with Jetpack Compose + Material 3, edge-to-edge rendering, and per-orientation system-bar visibility
- **MVVM with unidirectional data flow (UDF)** — each screen is driven by an immutable `UiState` exposed as a `StateFlow` by its `ViewModel`; events flow up, state flows down
- **Atomic UI decomposition + adaptive assemblies** — the screen entry dispatches by window size class to a `CompactAssembly` / `ExpandedAssembly`, which composes self-contained, single-responsibility components from `component/`; components depend strictly downward and never couple back to the assembly
- **Navigation3** with typed routes and an explicit back stack (double-back-to-exit on the root)
- **Koin** dependency injection, started in `Application.onCreate`
- **DataStore Preferences** persistence as the single source of truth behind a repository
- **Theme modes** — System / Light / Dark, with a circular reveal transition animation when switching
- **In-app language switching** — 简体中文 / English / Follow System, hot-swapped at runtime without recreating the Activity (per-language resource bundles disabled so switching always works)
- **Crash log manager** — uncaught and caught exceptions written to app-specific external storage, chaining to the system default handler; today's log can be shared from the Settings page
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
│       │   ├── data/                    # Data layer (single source of truth)
│       │   │   ├── repository/          #   SettingsRepository contract + DataStore impl
│       │   │   └── settings/            #   Settings keys, enums & state
│       │   ├── di/                      # Koin modules
│       │   ├── log/                     # CrashLogManager (crash & exception logging)
│       │   ├── navigation/              # Navigation3 typed routes + NavHost
│       │   ├── screens/                 # Feature modules
│       │   │   ├── home/                #   Home feature
│       │   │   │   ├── compact/         #     Narrow-window assembly
│       │   │   │   ├── expanded/        #     Wide-window assembly
│       │   │   │   └── component/       #     Semantic components
│       │   │   │       ├── welcome/     #       Welcome card
│       │   │   │       └── about/       #       About card
│       │   │   └── settings/            #   Settings feature
│       │   │       ├── compact/         #     Narrow-window assembly
│       │   │       ├── expanded/        #     Wide-window assembly
│       │   │       └── component/       #     Semantic components
│       │   │           ├── content/     #       Screen content (shared by assemblies)
│       │   │           ├── appearance/  #       Theme item + selection dialog
│       │   │           ├── language/    #       Language item + selection dialog
│       │   │           ├── appInfo/     #       About / version / update check
│       │   │           └── clickableItem/ #   Clickable settings row
│       │   ├── theme/                   # Material 3 color scheme & typography
│       │   ├── ui/                      # Shared UI
│       │   │   ├── icons/               #   Vector icons
│       │   │   ├── section/             #   SectionCard container
│       │   │   ├── topbar/              #   AppTopBar
│       │   │   ├── windowSize/          #   Window size classes
│       │   │   └── dialog/              #   SingleChoiceDialog
│       │   ├── update/                  # Latest-release check (GitHub API)
│       │   ├── utils/
│       │   │   └── localization/        #   In-app localization manager
│       │   ├── TemplateActivity.kt
│       │   └── TemplateApplication.kt
│       └── res/                         # Resources (values / values-en)
├── gradle/
│   ├── libs.versions.toml               # Version catalog (dependencies)
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Architecture

### State management — MVVM + UDF

The app follows **MVVM with unidirectional data flow (UDF)**, forming a closed loop where state flows down and events flow up:

- **View** (Composable) renders `UiState` and never mutates it directly.
- **ViewModel** owns a `MutableStateFlow<UiState>` — the single source of UI truth — exposed as an immutable `StateFlow`, and receives user intents as plain methods (`setThemeMode`, `setLanguage`).
- **Model** is the repository layer. `SettingsRepository` abstracts `DataStore Preferences`, which is the single source of truth for persisted settings; the repository is constructor-injected into the ViewModel via Koin (and swappable for tests).

The typical flow: `DataStore → Repository → ViewModel → UiState → UI` for state, and the reverse path for events.

### UI composition — atomic decomposition + adaptive assemblies

Code is organized with a **modular pattern driven by window size classes**, mirroring Material's adaptive guidance:

- `{ScreenName}Screen.kt` — a thin screen entry that hoists state and events, dispatches to an assembly by window size class, and hosts cross-form effects. It contains **no layout code**.
- `{ScreenName}CompactAssembly.kt` / `{ScreenName}ExpandedAssembly.kt` — own screen-level layout scaffolding (Scaffold, top bar, scroll container) and **assemble reusable atomic components**. The displayed form is decided jointly by window size class and screen rotation state; `if`-based layout branching is avoided.
- `component/<semantic-name>/` — atomic, single-responsibility UI units (`Welcome`, `About`, `Appearance`, `AppInfo`, …) named by semantics rather than generic suffixes. Dependencies point strictly downward: an assembly may compose components, but a component never composes back into an assembly, so the tree stays uncoupled.

Shared cross-feature code is hoisted to the top level (`data/`, `ui/`, `theme/`, `utils/`, `log/`, `update/`, `di/`); code used by a single feature stays inside that feature module.

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
- **Add a new screen**: create a `screens/<name>/` feature module with its `UiState` + `ViewModel` + `Compact`/`Expanded` assemblies and atomic components under `component/`, register the route in `navigation/Screen.kt`, and add it to `AppNavHost`.

## License

[MIT](LICENSE) © 2026 Evilgodxu
