# SOLID Rules
## SRP
Aplicação: classes por responsabilidade clara (controller adapta HTTP, use case orquestra, domínio contém regra pura). Violações: God service, use case anêmico que concentra tudo. Correção: extrair objetos de domínio e orquestradores menores. Alertas: classe muda por múltiplos motivos.
## OCP
Aplicação: extensões via ports/gateways e políticas de transição. Violações: if/switch por provider/status espalhado. Correção: strategy/policy por boundary real. Alertas: toda nova integração exige editar muitas classes.
## LSP
Aplicação: implementação de port deve respeitar contrato sem comportamento surpresa. Violações: provider dev simulando IA real sem deixar explícito. Correção: contratos explícitos e testes de contrato. Alertas: chamada equivalente gera erro inesperado.
## ISP
Aplicação: interfaces pequenas por capability real. Violações: interface gigante e genérica. Correção: separar contracts por caso de uso. Alertas: implementações com métodos não usados.
## DIP
Aplicação: application/domain depende de abstrações (ports), infra implementa. Violações: controller acessa repository, use case depende de JPA concreto, DTO HTTP no domínio. Correção: separar adapters/mappers e ports. Alertas: imports de framework no domínio.
Regras mandatórias: Controller chama use case; use case orquestra; domain puro; repository e provider externos como ports; DTO HTTP/JPA entity nunca no domínio; mapper sem regra de negócio relevante; config Spring não vaza ao domínio; interface só quando boundary real.
