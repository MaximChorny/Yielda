---
name: android-practices
description: Use for Stoxy Kotlin/Compose code quality, architecture, DI, testing, naming, and general Android best practices.
---

# Android Practices

Use this skill for general Kotlin and Android work in Stoxy.

## Workflow

1. Check whether the change is UI, data, domain, or infrastructure.
2. Prefer the existing local pattern unless it is clearly broken.
3. Keep logic small, explicit, and testable.
4. Use this skill together with the more specific skill for the concern involved.

## Core Rules

- Prefer `val` and immutable UI models by default.
- Keep Compose UI stateless where possible.
- Hoist state and side effects to the smallest reasonable owner.
- Keep DI, repositories, and data mapping out of UI composables.
- Use descriptive names over abbreviations.
- Avoid premature abstraction.

## Kotlin Rules

- Prefer expression bodies only when they stay readable.
- Use `when` as an expression for closed sets.
- Avoid `!!` and unchecked casts unless the invariant is enforced locally.
- Keep extension functions close to the domain they support.

## Testing Rules

- Add tests for business rules, regressions, and edge cases.
- Test state transitions, not just implementation details.
- Prefer focused unit tests over broad integration tests when possible.

## Project Notes

- Follow local coroutine helpers and lifecycle patterns already used in the project.
- Keep Android-only code out of common/shared logic unless the platform requires it.
