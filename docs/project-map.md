# Yielda Project Map

## Summary

Yielda is currently an Android app with a shared KMP `search` feature module, an iOS Xcode app shell, and project-local agent skills.

## Modules

### `:app`

- Path: [`app/`](../app/)
- Type: Android application
- Purpose: application shell, Compose UI entry point, resources, and tests
- Main source root: `app/src/main`
- Unit tests: `app/src/test`
- Instrumented tests: `app/src/androidTest`

### `:search`

- Path: [`search/`](../search/)
- Type: Kotlin Multiplatform library feature module
- Purpose: shared search state and shared search UI for iOS and Android, plus Android packaging/resources
- Main source roots: `search/src/commonMain`, `search/src/main`
- Unit tests: `search/src/test`
- Instrumented tests: `search/src/androidTest`

### `:ui`

- Path: [`ui/`](../ui/)
- Type: Kotlin Multiplatform UI library module
- Purpose: shared Compose UI foundation, theme, typography, and color tokens for Android and iOS
- Main source roots: `ui/src/commonMain`, `ui/src/androidMain`, `ui/src/iosMain`

### `iosApp`

- Path: [`iosApp/`](../iosApp/)
- Type: iOS Xcode application shell
- Purpose: launch and host the shared KMP UI on iOS
- Main source roots: `iosApp/iosApp`, `search/src/iosMain`

## Current Contents

- `app/src/main/java/com/stocks/yielda/MainActivity.kt` - app entry activity
- `app/src/main/res/` - app resources, launcher icons, strings, theme, colors, XML config
- `app/src/test/java/com/stocks/yielda/ExampleUnitTest.kt` - sample unit test
- `app/src/androidTest/java/com/stocks/yielda/ExampleInstrumentedTest.kt` - sample instrumented test
- `search/src/commonMain/kotlin/com/stocks/search/` - shared search state, DI, ViewModel, and screen UI
- `search/src/iosMain/kotlin/com/stocks/search/` - iOS Compose entrypoint for the search UI
- `search/src/main/` - Android manifest and resources for the search feature
- `ui/src/commonMain/kotlin/com/stocks/yielda/ui/` - shared Compose UI theme and design primitives
- `iosApp/iosApp/` - SwiftUI app shell embedding the shared Compose UI

## Agent Guidance

- Read this map before searching code.
- If you create a new module, create a matching module description file at the same time.
- Keep module descriptions short and factual:
  - purpose
  - main source roots
  - test roots
  - key responsibilities
  - any important dependencies or owners

## Preferred Description File

Create a sibling file under `docs/modules/` using the module name, for example:

- `docs/modules/app.md`
- `docs/modules/shared-network.md`

Each module description should be updated whenever the module’s scope or source layout changes.
