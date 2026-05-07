package com.clouddubber.infra.config;

import com.clouddubber.application.AudioExtractionPipelineService;
import com.clouddubber.application.DubbingService;
import com.clouddubber.application.TranscriptionPipelineService;
import com.clouddubber.domain.port.Ports;
import com.clouddubber.infra.provider.DevelopmentSpeechToTextGateway;
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
    @Bean TranscriptionPipelineService transcriptionPipelineService(Ports.DubbingJobRepository jobs, Ports.DubbingSegmentRepository segments, Ports.ObjectStorageGateway storage, Ports.SpeechToTextGateway stt, @Value("${clouddubber.transcription.language-hint:en}") String lang, @Value("${clouddubber.transcription.store-transcript-asset:false}") boolean store, @Value("${clouddubber.transcription.output-format:json}") String format, @Value("${clouddubber.pipeline.temp-directory:/tmp/clouddubber}") String tempDirectory){ return new TranscriptionPipelineService(jobs, segments, storage, stt, Instant::now, lang, store, format, Path.of(tempDirectory)); }
}
