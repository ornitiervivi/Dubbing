# cloud-dubber-api

Plataforma cloud-first para pipeline de dublagem assistida por IA com foco inicial em fundação técnica.

## Estado atual
Este repositório está na fase de **estabilização da fundação**: arquitetura, harness, sensores, CI, segurança e qualidade.

## Harness (Agent = Model + Harness)
O projeto usa harness explícito para guiar agentes:
- Regras operacionais em `AGENTS.md`.
- Políticas e guias em `docs/ai`.
- Sensores em `docs/ai/SENSORS.md`.
- Memória versionada em `docs/memory`.

## Como rodar localmente
1. Pré-requisitos:
   - Java 25
   - Maven 3.9+
   - Docker e Docker Compose
2. Executar sensores principais:
   - `mvn test`
   - `mvn verify`
   - `docker compose config`
   - `docker build -t cloud-dubber-api:local .`
3. Subir stack local:
   - `docker compose up -d`
4. Swagger:
   - `http://localhost:8080/swagger-ui.html`

## Como testar a API
- Smoke local pelo contexto Spring: `mvn test`
- Contrato OpenAPI via Swagger UI no endpoint acima.

## Roadmap
Roadmap de produto e limites de escopo em:
- `docs/product/ROADMAP.md`
- `docs/product/NON_GOALS.md`

## Regra de binários
Não versionar jars, classes, imagens, bancos locais, outputs de build ou outros binários.
