# `iosApp`

- Path: [`iosApp/`](../../iosApp/)
- Type: iOS Xcode application shell
- Purpose: launch and host the shared KMP UI on iOS
- Main source roots: `iosApp/iosApp`, `search/src/iosMain`
- Responsibilities:
  - boot the iOS app shell
  - embed the shared Compose UI from the `search` framework
  - keep iOS launch wiring separate from shared feature code
