package com.clouddubber.infra.config;

import com.clouddubber.application.AudioExtractionPipelineService;
import com.clouddubber.application.DubbingService;
import com.clouddubber.application.TranscriptionPipelineService;
import com.clouddubber.application.TranslationPipelineService;
import com.clouddubber.domain.model.TranslationModels;
import com.clouddubber.domain.port.Ports;
import com.clouddubber.infra.provider.DevelopmentScriptTranslationGateway;
import com.clouddubber.infra.provider.DevelopmentSpeechToTextGateway;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    DubbingService dubbingService(
            Ports.DubbingJobRepository jobs,
            Ports.DubbingSegmentRepository segments,
            Ports.VoiceProfileRepository voices,
            Ports.ObjectStorageGateway storage,
            Ports.DubbingPipelineQueue queue
    ) {
        return new DubbingService(jobs, segments, voices, storage, queue, Instant::now, () -> UUID.randomUUID().toString());
    }

    @Bean
    AudioExtractionPipelineService audioExtractionPipelineService(
            Ports.DubbingJobRepository jobs,
            Ports.ObjectStorageGateway storage,
            Ports.AudioExtractionGateway extractionGateway,
            @Value("${clouddubber.ffmpeg.audio-format:mp3}") String format,
            @Value("${clouddubber.ffmpeg.audio-codec:libmp3lame}") String codec,
            @Value("${clouddubber.ffmpeg.timeout:PT30S}") Duration timeout,
            @Value("${clouddubber.pipeline.temp-directory:/tmp/clouddubber}") String tempDirectory
    ) {
        return new AudioExtractionPipelineService(jobs, storage, extractionGateway, Instant::now, format, codec, timeout, Path.of(tempDirectory));
    }

    @Bean
    Ports.SpeechToTextGateway speechToTextGateway() {
        return new DevelopmentSpeechToTextGateway();
    }

    @Bean
    Ports.ScriptTranslationGateway scriptTranslationGateway() {
        return new DevelopmentScriptTranslationGateway();
    }

    @Bean
    TranslationPipelineService translationPipelineService(
            Ports.DubbingJobRepository jobs,
            Ports.DubbingSegmentRepository segments,
            Ports.ObjectStorageGateway storage,
            Ports.ScriptTranslationGateway translationGateway,
            @Value("${clouddubber.translation.source-language:en}") String source,
            @Value("${clouddubber.translation.target-language:pt-BR}") String target,
            @Value("${clouddubber.translation.preserved-terms:Black Mage,White Mage,Red Mage,Blue Mage,DPS,rotation,spell,skill,job,class,build,buff,debuff,cooldown,cast,caster,healer,tank,party,raid,boss,AoE,DoT,proc}") String preservedTerms,
            @Value("${clouddubber.translation.store-translated-script-asset:false}") boolean storeAsset,
            @Value("${clouddubber.translation.output-format:json}") String outputFormat
    ) {
        return new TranslationPipelineService(
                jobs,
                segments,
                storage,
                translationGateway,
                Instant::now,
                source,
                target,
                parsePreservedTerms(preservedTerms),
                storeAsset,
                outputFormat
        );
    }

    @Bean
    TranscriptionPipelineService transcriptionPipelineService(
            Ports.DubbingJobRepository jobs,
            Ports.DubbingSegmentRepository segments,
            Ports.ObjectStorageGateway storage,
            Ports.SpeechToTextGateway speechToTextGateway,
            @Value("${clouddubber.transcription.language-hint:en}") String languageHint,
            @Value("${clouddubber.transcription.store-transcript-asset:false}") boolean storeAsset,
            @Value("${clouddubber.transcription.output-format:json}") String outputFormat,
            @Value("${clouddubber.pipeline.temp-directory:/tmp/clouddubber}") String tempDirectory
    ) {
        return new TranscriptionPipelineService(
                jobs,
                segments,
                storage,
                speechToTextGateway,
                Instant::now,
                languageHint,
                storeAsset,
                outputFormat,
                Path.of(tempDirectory)
        );
    }

    private List<TranslationModels.PreservedTerm> parsePreservedTerms(String preservedTerms) {
        return Arrays.stream(preservedTerms.split(","))
                .map(String::trim)
                .filter(term -> !term.isBlank())
                .map(TranslationModels.PreservedTerm::new)
                .toList();
    }
}
