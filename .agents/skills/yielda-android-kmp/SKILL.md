---
name: yielda-android-kmp
description: Use for Stoxy Android/KMP implementation and review involving Compose UI, ViewModels, coroutines/Flow, Ktor networking, and navigation.
---

# Yielda Android/KMP

Use this skill for work in the Stoxy project. Prefer KMP-compatible APIs and shared code when the
change is not platform-specific.

## Workflow

1. Identify whether the change belongs to UI, shared domain/data, or platform-specific code.
2. Read only the matching reference file(s) below.
3. Prefer existing local patterns over introducing a new architecture.
4. If local code conflicts with these rules, treat it as a project exception and verify before
   changing it.

## References

- General Kotlin/Compose/Android guidance: `../android-practices/SKILL.md`
- Compose/UI, state, screen structure: `references/android-compose.md`
- Ktor networking, serialization, engines, DI: `../ktor-kmp/SKILL.md`
- ViewModel, coroutines, Flow, navigation, side effects: `../viewmodel-flow-navigation/SKILL.md`
- Testing, regressions, coroutine and Compose tests: `../testing-quality/SKILL.md`
- Layering, coupling, and dependency boundaries: `../architecture-review/SKILL.md`
