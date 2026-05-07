# Skill
name: api-design
description: Skill de api-design.
when_to_use: quando a tarefa exigir api-design.
when_not_to_use: fora do domínio api-design.
required_context: AGENTS, specs, ADRs, memory.
required_steps: planejar, implementar, testar, documentar.
allowed_patterns: clean architecture, validação explícita, testes.
forbidden_patterns: acoplamento indevido, atalhos sem teste.
required_tests: unit e integração aplicáveis.
required_commands: mvn test, mvn verify.
acceptance_criteria: entrega validada por sensores.