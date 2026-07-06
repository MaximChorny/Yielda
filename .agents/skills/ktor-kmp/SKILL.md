---
name: ktor-kmp
description: Use for Stoxy Ktor KMP networking, HTTP clients, serialization, error mapping, and repository data access.
---

# Ktor KMP

Use this skill when adding or changing network code in Stoxy.

## Workflow

1. Keep HTTP concerns in the data layer.
2. Use a shared KMP client in common code and platform engines in platform source sets.
3. Keep request/response DTOs serializable and stable.
4. Map transport failures to domain or UI-safe errors before they cross layers.

## Rules

- Use `io.ktor:ktor-client-core` from shared code.
- Install `ContentNegotiation` for JSON APIs.
- Keep auth, base URL, retries, and headers centralized.
- Use suspend functions for repository APIs.
- Do not expose `HttpClient`, request builders, or Ktor exceptions in UI state.

## Defaults

- Prefer a single client factory per app or data module.
- Add explicit timeouts where the backend needs them.
- Use `kotlinx.serialization` models for request and response payloads.
