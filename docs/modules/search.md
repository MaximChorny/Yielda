# `:search`

- Path: [`search/`](../../search/)
- Type: Kotlin Multiplatform feature module
- Purpose: shared search state, Finnhub repository, Ktor client, and shared search UI
- Main source roots: `search/src/commonMain`, `search/src/iosMain`, `search/src/main`
- Unit tests: `search/src/test`
- Instrumented tests: `search/src/androidTest`
- Package: `com.stocks.yielda.search`
- Responsibilities:
  - own shared search state, module wiring, ViewModel, repository, and screen UI
  - keep Finnhub API access and serialization in the shared data layer
  - expose a framework-friendly API for Android and iOS consumers
