# `:search`

- Path: [`search/`](../../search/)
- Type: Kotlin Multiplatform feature module
- Purpose: shared search state and shared search UI, plus Android packaging/resources
- Main source roots: `search/src/commonMain`, `search/src/iosMain`, `search/src/main`
- Unit tests: `search/src/test`
- Instrumented tests: `search/src/androidTest`
- Package: `com.stocks.yielda.search`
- Responsibilities:
  - own shared search state, module wiring, ViewModel, and screen UI
  - keep platform-specific packaging/resources isolated from shared UI
  - expose a framework-friendly API for iOS consumers
