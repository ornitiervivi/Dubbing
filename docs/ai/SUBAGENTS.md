# Subagents

Use subagents em tarefas de estabilização multi-domínio.

## Protocolo obrigatório de revisão
Cada subagent deve registrar:
1. Escopo da revisão.
2. Arquivos analisados.
3. Problemas encontrados.
4. Propostas de correção sem ampliar escopo funcional.

## Subagents padrão
- Harness Reviewer
- Architecture Guardian
- Security Reviewer
- Testing Reviewer
- API Reviewer
- Persistence Reviewer
- DevOps Reviewer
- Documentation Reviewer

## Regras de execução
- Ownership por área para evitar conflito.
- Consolidação final feita pelo agente principal.
- Nenhum subagent pode introduzir feature nova de produto.
