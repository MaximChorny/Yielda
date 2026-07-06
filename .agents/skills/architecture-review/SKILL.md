---
name: architecture-review
description: Use for Stoxy codebase structure reviews, layering decisions, dependency boundaries, and refactor guidance.
---

# Architecture Review

Use this skill when deciding where code should live or whether a change preserves good structure.

## Workflow

1. Identify the layer that owns the behavior.
2. Check whether the change crosses UI, presentation, domain, data, or platform boundaries.
3. Prefer the smallest refactor that restores clarity.
4. Preserve local conventions unless they are actively causing coupling or duplication.

## Rules

- UI owns rendering and user interaction only.
- ViewModels own screen state and orchestration.
- Repositories own data access and remote/local source coordination.
- Domain code should stay free of Android framework dependencies when possible.
- Avoid introducing new abstractions unless they remove real duplication or coupling.

## Review Questions

- Is this logic in the right layer?
- Does this dependency point in the correct direction?
- Will another feature be able to reuse this without leaking platform details?
- Can the same behavior be expressed with fewer moving parts?
