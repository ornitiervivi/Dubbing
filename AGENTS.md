# AGENTS
Este arquivo é a constituição do repositório para agentes de IA.

## Ordem obrigatória de leitura
1. Antes de qualquer alteração, leia AGENTS.md.
2. Depois leia docs/ai/AI_ENGINEERING_RULES.md.
3. Depois leia docs/ai/HARNESS.md.
4. Depois leia docs/ai/SENSORS.md.
5. Depois leia docs/ai/SUBAGENTS.md.
6. Depois leia docs/ai/SKILLS.md.
7. Depois leia a skill específica da tarefa.
8. Depois leia docs/specs.
9. Depois leia docs/adr.
10. Depois leia docs/memory.

## Regras mandatórias
- Nunca implemente sem contexto.
- Nunca implemente sem plano.
- Nunca altere arquitetura sem ADR.
- Nunca altere comportamento público sem atualizar docs.
- Nunca finalize sem rodar sensores.
- Nunca usar Kotlin.
- Nunca usar Java record.
- Nunca colocar comentários no código.
- Nunca fingir IA real com mock de produção.
- Nunca quebrar Clean Architecture.
- Nunca deixar domínio depender de Spring.
- Nunca deixar Controller com regra de negócio.
- Nunca deixar DTO HTTP entrar no domínio.
- Nunca usar JPA entity como domínio.
- Nunca versionar secrets.
- Sempre executar testes.
- Sempre informar comandos executados.
- Sempre informar arquivos alterados.
- Sempre informar riscos.
- Sempre informar skills consultadas.
- Sempre atualizar memory quando houver aprendizado, bug, limitação ou decisão relevante.


## PR automation rules
- Antes de criar PR, verificar binários.
- Antes de criar PR, rodar testes.
- Antes de criar PR, rodar build.
- Se CI falhar, corrigir a branch do PR, não criar PR novo.
- Se review do Codex apontar P0 ou P1, corrigir antes de pedir merge.
- Nunca commitar binários.
- Nunca commitar secrets.
- Nunca fazer push direto na main.
- Pull Requests criados pelo Codex Web não podem conter arquivos binários.
- Não commitar build outputs.
- Não commitar imagens, ícones, bancos locais, jars, zips, apks, executáveis ou arquivos gerados.
- Não commitar Maven Wrapper jar.
- GitHub Actions deve usar mvn diretamente.
- Binários gerados devem ser publicados como GitHub Actions artifacts.
- Antes de criar PR, o agente deve rodar uma verificação de binários.
- Se algum binário for detectado, o agente deve remover, ignorar ou documentar o motivo e parar.

## Senior Engineering Standards
- Todo código deve ser escrito como produção.
- Toda classe deve ter responsabilidade clara.
- Todo método deve ser pequeno e expressivo.
- Todo use case deve representar intenção de negócio.
- Todo boundary deve ser explícito.
- Todo provider externo deve passar por port/gateway.
- Todo código novo deve ter teste proporcional.
- Toda refatoração deve preservar comportamento.
- Todo pattern deve justificar redução de complexidade/acoplamento.
- Se uma classe parecer ruim, refatorar antes de adicionar feature.
- Se encontrar smell, registrar em docs/memory/LEARNINGS.md ou corrigir.
- Se encontrar dívida não corrigida agora, registrar em docs/memory/LIMITATIONS.md.
- Antes de finalizar, rodar quality review usando docs/quality/CODE_REVIEW_RUBRIC.md.
