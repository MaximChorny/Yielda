# Android Compose

## Rules

- Hoist state and keep UI models immutable.
- Split screens into route, state holder, and stateless content when it improves clarity.
- Keep navigation and data loading out of pure UI composables.
- Prefer small, previewable composables over large feature functions.
- Use clear resource names and simple, explicit UI state.

## Good defaults

- `val` over `var`
- `StateFlow` or immutable state objects for screen state
- one-way data flow from ViewModel to UI
- side effects collected at the screen/host layer
