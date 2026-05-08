# BUG LOG

## 2026-05-07 - Build Maven com plugins sem versão explícita
- Sintoma: warnings de modelo Maven malformado por ausência de versão em plugins.
- Causa: `maven-compiler-plugin` e `spring-boot-maven-plugin` sem versionamento explícito.
- Correção: pinagem de versão no `pom.xml`.

## 2026-05-08 Engineering quality hardening
Atualizado para incluir gates senior/staff, regras explícitas, skills e ADRs de qualidade.

- 2026-05-08: Violação de regra absoluta detectada: uso de Java record em classes de domínio e ports.
- 2026-05-08: Após resolução manual de conflito, `JpaVoiceProfileRepository.save` ficou com bloco duplicado misturando acesso por métodos (`id()`) e campos privados (`id`), quebrando compilação com erros de private access e variável duplicada.
- Correção: remover o bloco duplicado e manter apenas mapeamento via métodos públicos do domínio.
