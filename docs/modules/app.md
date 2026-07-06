# `:app`

- Path: [`app/`](../../app/)
- Type: Android application
- Purpose: Yielda application shell and Compose entry point
- Main source root: `app/src/main`
- Unit tests: `app/src/test`
- Instrumented tests: `app/src/androidTest`
- Package: `com.stocks.yielda`
- Responsibilities:
  - start the app
  - host top-level UI
  - own app resources
  - keep feature wiring at the application boundary
