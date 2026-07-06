# ViewModel, Flow, Navigation

## Rules

- Use KMP-friendly ViewModel artifacts for shared code.
- Prefer `StateFlow` for state and `SharedFlow` or an event wrapper for one-shot effects.
- Use `viewModelScope` for coroutine work that belongs to the ViewModel.
- Keep navigation in the screen/host layer, not in repositories or use cases.
- Cancel or replace in-flight jobs when the UI action is repeated.

## Good defaults

- expose immutable state from the ViewModel
- keep side effects explicit
- map domain errors to UI state in the presentation layer
