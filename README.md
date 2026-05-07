# LocalDubber

LocalDubber é um app Android nativo para criar e gerenciar projetos de dublagem local a partir de vídeos do usuário.

## MVP atual
- Seleciona vídeo local com seletor do Android.
- Cria projeto com status inicial `CREATED`.
- Persiste projetos localmente com Room.
- Lista projetos criados.
- Mostra detalhes do projeto, URI, status e timeline de etapas planejadas.
- Gera APK debug localmente e no GitHub Actions.

## O que ainda não faz
- Extração real de áudio.
- Transcrição real.
- Tradução real.
- Geração de voz.
- Renderização final de vídeo.

## Como rodar localmente
1. Abra no Android Studio.
2. Sincronize o Gradle.
3. Execute em emulador/dispositivo Android.

## Testes
```bash
./gradlew test
```

## Gerar APK debug
```bash
./gradlew assembleDebug
```
APK gerado em:
`app/build/outputs/apk/debug/app-debug.apk`

## CI e artifacts
O workflow está em `.github/workflows/android-ci.yml`.
Em cada execução (push/pull_request), o artifact `LocalDubber-debug-apk` fica disponível na aba **Actions** do GitHub.

## Roadmap
- Fase 1: base Android, criação/persistência/lista/detalhes e CI com APK.
- Fase 2: extração de áudio real e tratamento robusto de erro.
- Fase 3: transcrição local/híbrida com segmentos.
- Fase 4: tradução/adaptação PT-BR.
- Fase 5: geração de voz com consentimento explícito.
- Fase 6: renderização e exportação MP4.
- Fase 7: versão Windows standalone local-first.
- Fase 8: editor avançado de segmentos e timing.
