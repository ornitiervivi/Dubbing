package com.clouddubber.infra.config;

import com.clouddubber.application.AudioExtractionPipelineService;
import com.clouddubber.application.DubbingService;
import com.clouddubber.application.TranscriptionPipelineService;
import com.clouddubber.application.TranslationPipelineService;
import com.clouddubber.domain.port.Ports;
import com.clouddubber.infra.provider.DevelopmentSpeechToTextGateway;
import com.clouddubber.infra.provider.DevelopmentScriptTranslationGateway;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean DubbingService dubbingService(Ports.DubbingJobRepository a, Ports.DubbingSegmentRepository b, Ports.VoiceProfileRepository c, Ports.ObjectStorageGateway d, Ports.DubbingPipelineQueue e) { return new DubbingService(a,b,c,d,e, Instant::now, () -> UUID.randomUUID().toString()); }
    @Bean AudioExtractionPipelineService audioExtractionPipelineService(Ports.DubbingJobRepository jobs, Ports.ObjectStorageGateway storage, Ports.AudioExtractionGateway extractionGateway, @Value("${clouddubber.ffmpeg.audio-format:mp3}") String format, @Value("${clouddubber.ffmpeg.audio-codec:libmp3lame}") String codec, @Value("${clouddubber.ffmpeg.timeout:PT30S}") Duration timeout, @Value("${clouddubber.pipeline.temp-directory:/tmp/clouddubber}") String tempDirectory) { return new AudioExtractionPipelineService(jobs, storage, extractionGateway, Instant::now, format, codec, timeout, Path.of(tempDirectory)); }
    @Bean Ports.SpeechToTextGateway speechToTextGateway(){ return new DevelopmentSpeechToTextGateway(); }
    @Bean Ports.ScriptTranslationGateway scriptTranslationGateway(){ return new DevelopmentScriptTranslationGateway(); }
    @Bean TranslationPipelineService translationPipelineService(Ports.DubbingJobRepository jobs, Ports.DubbingSegmentRepository segments, Ports.ObjectStorageGateway storage, Ports.ScriptTranslationGateway translationGateway, @Value("${clouddubber.translation.source-language:en}") String source, @Value("${clouddubber.translation.target-language:pt-BR}") String target, @Value("${clouddubber.translation.preserved-terms:Black Mage,White Mage,Red Mage,Blue Mage,DPS,rotation,spell,skill,job,class,build,buff,debuff,cooldown,cast,caster,healer,tank,party,raid,boss,AoE,DoT,proc}") String preserved, @Value("${clouddubber.translation.store-translated-script-asset:false}") boolean store, @Value("${clouddubber.translation.output-format:json}") String format){ return new TranslationPipelineService(jobs, segments, storage, translationGateway, Instant::now, source, target, java.util.Arrays.stream(preserved.split(",")).map(String::trim).filter(it->!it.isBlank()).map(com.clouddubber.domain.model.TranslationModels.PreservedTerm::new).toList(), store, format); }
    @Bean TranscriptionPipelineService transcriptionPipelineService(Ports.DubbingJobRepository jobs, Ports.DubbingSegmentRepository segments, Ports.ObjectStorageGateway storage, Ports.SpeechToTextGateway stt, @Value("${clouddubber.transcription.language-hint:en}") String lang, @Value("${clouddubber.transcription.store-transcript-asset:false}") boolean store, @Value("${clouddubber.transcription.output-format:json}") String format, @Value("${clouddubber.pipeline.temp-directory:/tmp/clouddubber}") String tempDirectory){ return new TranscriptionPipelineService(jobs, segments, storage, stt, Instant::now, lang, store, format, Path.of(tempDirectory)); }
}

