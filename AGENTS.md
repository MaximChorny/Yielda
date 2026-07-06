# Yielda Agent Guidance

This project uses two sources of agent guidance:

1. Local project skills under `.agents/skills/`
2. The local Android skills checkout at `/Users/maksymchornyi/StudioProjects/skills`

## Discovery Order

1. Read [`docs/project-map.md`](docs/project-map.md) first to understand the module layout.
2. Read `.agents/skills/yielda-android-kmp/SKILL.md` for project-specific rules.
3. For Android best practices, read the bridge skill under `.agents/skills/android-skills-bridge/`.
4. For task-specific Android guidance, read the matching file from `/Users/maksymchornyi/StudioProjects/skills`.
5. Prefer the smallest relevant skill or reference file.

## External Android Skills

Use these local Android skills for common concerns:

- `/Users/maksymchornyi/StudioProjects/skills/devtools/android-cli/SKILL.md`
- `/Users/maksymchornyi/StudioProjects/skills/navigation/navigation-3/SKILL.md`
- `/Users/maksymchornyi/StudioProjects/skills/testing/testing-setup/SKILL.md`
- `/Users/maksymchornyi/StudioProjects/skills/jetpack-compose/migration/migrate-xml-views-to-jetpack-compose/SKILL.md`
- `/Users/maksymchornyi/StudioProjects/skills/system/edge-to-edge/SKILL.md`

## Local Project Skills

- `.agents/skills/yielda-android-kmp/SKILL.md`
- `.agents/skills/android-practices/SKILL.md`
- `.agents/skills/ktor-kmp/SKILL.md`
- `.agents/skills/viewmodel-flow-navigation/SKILL.md`
- `.agents/skills/testing-quality/SKILL.md`
- `.agents/skills/architecture-review/SKILL.md`
- `.agents/skills/android-skills-bridge/SKILL.md`

## Module Documentation Rule

When an agent creates a new module, it must also create a matching module description file under
`docs/modules/` in the same change.
