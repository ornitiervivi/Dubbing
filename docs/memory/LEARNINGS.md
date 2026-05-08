# LEARNINGS

- Separação domínio/aplicação/infra acelera evolução sem acoplamento.

## 2026-05-08 Engineering quality hardening
Atualizado para incluir gates senior/staff, regras explícitas, skills e ADRs de qualidade.

- 2026-05-08: Regra “não usar Java record” precisa de sensor automático dedicado no CI para evitar regressão.

- 2026-05-08: Conflito de merge em `JpaVoiceProfileRepository.save` duplicou bloco de mapeamento (variável `entity` redefinida + acesso a campos privados de `VoiceProfile`), quebrando compilação no CI; correção foi manter apenas mapeamento via getters.
