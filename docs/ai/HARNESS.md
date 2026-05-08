# Harness

Harness é a parte operacional do agente. O modelo sozinho não garante qualidade.

## Definição
**Agent = Model + Harness**.

- **Model**: capacidade de raciocínio e geração.
- **Harness**: contexto, regras, sensores, memória, skills e ferramentas que restringem e validam o trabalho.

## Componentes obrigatórios do harness
1. **Guides**: `AGENTS.md`, specs, ADRs, docs de qualidade e segurança.
2. **Sensors**: comandos automatizados que barram regressão.
3. **Memory**: logs versionados de decisões, bugs, aprendizados e limitações.
4. **Subagents**: revisão especializada por domínio.
5. **Tools/MCP**: integrações permitidas pelo repositório.

## Fluxo mínimo antes de PR
1. Ler contexto obrigatório definido em `AGENTS.md`.
2. Declarar plano de revisão/execução.
3. Executar sensores mandatórios.
4. Registrar mudanças arquiteturais e decisões em docs/memory.
5. Validar checklist de segurança e qualidade antes de abrir PR.

## 2026-05-08 Engineering quality hardening
Atualizado para incluir gates senior/staff, regras explícitas, skills e ADRs de qualidade.
