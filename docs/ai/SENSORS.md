# Sensors
Sensores obrigatórios: mvn test, mvn verify, ArchUnit tests, Testcontainers integration tests, docker compose config, docker build, validação OpenAPI quando possível, security checks quando possível, eval fixtures para IA, PR checklist, Definition of Done.

## Regra permanente de binários
Pull Requests criados pelo Codex Web não podem conter arquivos binários.
- Não commitar build outputs.
- Não commitar imagens, ícones, bancos locais, jars, zips, apks, executáveis ou arquivos gerados.
- Não commitar Maven Wrapper jar.
- GitHub Actions deve usar mvn diretamente.
- Binários gerados devem ser publicados como GitHub Actions artifacts.
- Antes de criar PR, rodar verificação de binários.
- Se binário for detectado, remover, ignorar ou documentar motivo e parar.
