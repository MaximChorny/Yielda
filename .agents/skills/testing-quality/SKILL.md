---
name: testing-quality
description: Use for Stoxy unit tests, coroutine tests, Compose tests, regression coverage, and test design.
---

# Testing Quality

Use this skill when adding or reviewing tests in Stoxy.

## Workflow

1. Test the behavior the user sees or the business rule that matters.
2. Prefer the narrowest test that proves the contract.
3. Cover regressions and edge cases before adding happy-path-only tests.
4. Keep test data small and explicit.

## Rules

- Test state changes, outputs, and side effects.
- Prefer unit tests for ViewModels, use cases, and mapping logic.
- Use coroutine test dispatchers and deterministic scheduling for async code.
- Keep Compose tests focused on user-visible behavior.
- Avoid overspecifying implementation details.

## Good Defaults

- one assertion group per behavior
- clear test names describing the scenario
- fake collaborators over real network or database access
- explicit setup for time, dispatchers, and lifecycle-sensitive code
