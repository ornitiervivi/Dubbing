# 0021 - Store original transcript as JSON asset
## Status
Accepted
## Context
Precisamos armazenar transcrição opcionalmente no object storage para auditoria e reprocessamento.
## Decision
Formato padrão JSON com resumo de idioma, duração e quantidade de segmentos.
## Consequences
Mais robusto para evolução e leitura por máquinas, mantendo integração simples.
## Alternatives considered
SRT como padrão; adiado para evolução futura.
## Related documents
docs/architecture/API_CONTRACTS.md, docs/architecture/PIPELINE.md
