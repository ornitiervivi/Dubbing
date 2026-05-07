# BUG LOG

## 2026-05-07 - Build Maven com plugins sem versão explícita
- Sintoma: warnings de modelo Maven malformado por ausência de versão em plugins.
- Causa: `maven-compiler-plugin` e `spring-boot-maven-plugin` sem versionamento explícito.
- Correção: pinagem de versão no `pom.xml`.
