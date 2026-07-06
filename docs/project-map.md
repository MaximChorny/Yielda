# Yielda Project Map

## Summary

Yielda is currently a single-module Android app with project-local agent skills.

## Modules

### `:app`

- Path: [`app/`](../app/)
- Type: Android application
- Purpose: application shell, Compose UI entry point, resources, and tests
- Main source root: `app/src/main`
- Unit tests: `app/src/test`
- Instrumented tests: `app/src/androidTest`

## Current Contents

- `app/src/main/java/com/stocks/yielda/MainActivity.kt` - app entry activity
- `app/src/main/res/` - app resources, launcher icons, strings, theme, colors, XML config
- `app/src/test/java/com/stocks/yielda/ExampleUnitTest.kt` - sample unit test
- `app/src/androidTest/java/com/stocks/yielda/ExampleInstrumentedTest.kt` - sample instrumented test

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
