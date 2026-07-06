# Ktor KMP

## Rules

- Use `io.ktor:ktor-client-core` in shared code.
- Add the platform engine in the platform source set, not in common code.
- Keep request/response models serializable with `kotlinx.serialization`.
- Install `ContentNegotiation` for JSON APIs.
- Keep Ktor types inside the data layer; do not leak them into UI code.

## Good defaults

- shared `HttpClient` factory
- suspend APIs in repositories
- one place for base URL, auth headers, and error mapping
