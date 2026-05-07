# 0022 - use structured script translation contract
## Status
Accepted
## Context
Translation/adaptation requires deterministic validation per segment.
## Decision
Use `ScriptTranslationRequest` and `ScriptTranslationResult` as strict application boundary.
## Consequences
Validation of segment count/index and non-empty translated/adapted fields is mandatory.
## Alternatives considered
Free-text response parsing rejected due to fragility.
## Related documents
- docs/specs/PIPELINE_SPECIFICATION.md
- docs/provider-registry/OPENAI.md
