# 0020 - Use structured transcription contract
## Status
Accepted
## Context
Fase 3 precisa de fronteira estável entre provider STT e aplicação para gerar segmentos com timestamp.
## Decision
Usar SpeechToTextRequest, SpeechToTextResult e TranscribedSegment como contrato obrigatório no domínio.
## Consequences
Validação centralizada dos segmentos antes de persistência e menor acoplamento com formato de provider.
## Alternatives considered
Passar JSON bruto do provider; descartado por fragilidade e vazamento de infraestrutura.
## Related documents
docs/architecture/PIPELINE.md, docs/specs/PIPELINE_SPECIFICATION.md
