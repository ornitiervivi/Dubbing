# 0023 - use prompt registry for translation and adaptation
## Status
Accepted
## Context
Prompt evolution needs versioning and traceability.
## Decision
Translation/adaptation prompts are registered and versioned in `docs/prompts`.
## Consequences
Provider adapters must reference registry prompts instead of hardcoded prompt strings.
## Alternatives considered
Inline prompts in code were rejected due to low governance.
## Related documents
- docs/prompts/PROMPT_REGISTRY.md
- docs/prompts/VERSIONING.md
