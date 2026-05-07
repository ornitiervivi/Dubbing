# cloud-dubber-api
Plataforma web/cloud-first de dublagem automática por IA.

## Fase atual
Harness completo e fundação da API em Java 25 + Spring Boot 4.

## Fluxo de PR assistido por IA
1. Codex trabalha em branch de feature e nunca faz push direto na main.
2. Antes do PR, Codex roda verificação de binários, testes e build.
3. Codex abre ou atualiza o PR na mesma branch até CI ficar verde.
4. Se houver review com P0/P1, Codex corrige antes de pedir merge.
5. Binários devem ser artifacts de workflow, nunca arquivos versionados.

## Como pedir correção
Comente no PR: `@codex fix the CI failures`.

## Auto-merge no GitHub
Ative auto-merge somente após todos os checks obrigatórios passarem e reviews obrigatórios serem aprovados.

## Local
- `mvn test`
- `mvn verify`
- `docker compose up -d`
- Swagger: http://localhost:8080/swagger-ui.html

## Regra de binários
Não versionar jars, classes, imagens, bancos locais, outputs de build ou outros binários.
