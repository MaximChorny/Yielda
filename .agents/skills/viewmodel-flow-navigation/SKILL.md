---
name: viewmodel-flow-navigation
description: Use for Stoxy ViewModel state, coroutines, Flow, one-shot events, and navigation handling.
---

# ViewModel, Flow, Navigation

Use this skill when changing presentation logic or navigation.

## Workflow

1. Keep state in the ViewModel and navigation in the screen/host layer.
2. Choose `StateFlow` for state and an explicit event channel for one-shot actions.
3. Prefer cancel/restart behavior for repeatable actions.
4. Read the navigation-specific rules before adding routes or flow wiring.

## Rules

- Prefer KMP-friendly ViewModel APIs for shared code.
- Expose immutable UI state.
- Model one-shot effects separately from long-lived state.
- Use structured concurrency for async work.
- Cancel old jobs when a new user action supersedes them.
- Keep navigation out of repositories and use cases.

## Good Patterns

- `StateFlow` for screen state
- `SharedFlow` or event wrappers for navigation and dialogs
- `viewModelScope` for ViewModel-owned work
- screen-layer collectors for navigation effects
