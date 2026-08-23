# `:ui`

- Path: [`ui/`](../../ui/)
- Type: Kotlin Multiplatform UI library module
- Purpose: shared Compose UI foundation for Android and iOS
- Main source roots: `ui/src/commonMain`, `ui/src/androidMain`, `ui/src/iosMain`
- Package: `com.stocks.yielda.ui`
- Responsibilities:
  - own shared Material theme, typography, and color tokens
  - package Montserrat as the shared app font family
  - expose `YieldaTheme.colorScheme`, `YieldaTheme.typography`, and `YieldaTheme.typogrephy` as the public design API
  - keep platform-specific UI behavior behind expect/actual boundaries
  - provide reusable UI primitives for feature modules
