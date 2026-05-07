# Sensors

Sensores são gates de qualidade e devem executar local e em CI.

## Sensores mandatórios deste repositório
- `git status --short`
- `git diff --name-only`
- `git diff --numstat`
- `mvn test`
- `mvn verify`
- `docker compose config`
- `docker build -t cloud-dubber-api:local .`

## Sensores complementares recomendados
- Testes ArchUnit para regras de Clean Architecture.
- Testes de integração com Testcontainers quando houver infra dependente.
- Validação de OpenAPI.
- Checklist de segurança (`docs/security/SECURITY_CHECKLIST.md`).

## Regra permanente de binários
Pull Requests criados pelo Codex Web não podem conter arquivos binários.
- Não commitar build outputs.
- Não commitar imagens, ícones, bancos locais, jars, zips, apks, executáveis ou arquivos gerados.
- Não commitar Maven Wrapper jar.
- GitHub Actions deve usar `mvn` diretamente.
- Binários gerados devem ser publicados como GitHub Actions artifacts.
- Antes de criar PR, rodar verificação de binários.
- Se binário for detectado, remover, ignorar ou documentar motivo e parar.
