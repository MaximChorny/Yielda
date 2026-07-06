---
name: android-skills-bridge
description: Use for Stoxy Android work when you want the local Android skills repository to be the source of truth for platform-specific guidance.
---

# Android Skills Bridge

Use this skill as the entry point to the local Android skills checkout.

## Source of Truth

- Android skills repo: `/Users/maksymchornyi/StudioProjects/skills`
- Repo overview: `/Users/maksymchornyi/StudioProjects/skills/README.md`

## Workflow

1. Read the repo README first.
2. Then open the most specific skill for the task.
3. Use project-local `Stoxy` skills together with the external Android skill when both apply.
4. Prefer the external Android skill for platform best practices, especially when it is more specific.

## Common Entrypoints

- CLI and environment setup: `devtools/android-cli/SKILL.md`
- Navigation patterns and migration: `navigation/navigation-3/SKILL.md`
- Testing setup and strategy: `testing/testing-setup/SKILL.md`
- Compose migration: `jetpack-compose/migration/migrate-xml-views-to-jetpack-compose/SKILL.md`
- Edge-to-edge/system UI: `system/edge-to-edge/SKILL.md`
