package com.clouddubber.domain.port;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.SpeechModels;
import com.clouddubber.domain.model.TranslationModels;
import com.clouddubber.domain.model.VoiceProfile;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public final class Ports {
    private Ports() {}

    public interface DubbingJobRepository {
        DubbingJob save(DubbingJob job);
        Optional<DubbingJob> findById(String id);
        List<DubbingJob> search(int page, int size);
        List<DubbingJob> findByStatus(Enums.DubbingJobStatus status, int limit);
    }

    public interface DubbingSegmentRepository {
        List<DubbingSegment> findByJobId(String jobId);
        Optional<DubbingSegment> findById(String segmentId);
        DubbingSegment save(DubbingSegment segment);
        boolean existsByJobIdAndSegmentIndex(String jobId, int segmentIndex);
    }

    public interface VoiceProfileRepository {
        VoiceProfile save(VoiceProfile vp);
        Optional<VoiceProfile> findById(String id);
    }

    public static final class AssetReference {
        private final String key;
        private final String contentType;

        public AssetReference(String key, String contentType) {
            this.key = key;
            this.contentType = contentType;
        }

        public String key() { return key; }
        public String contentType() { return contentType; }
    }

    public interface ObjectStorageGateway {
        String upload(String key, InputStream in, long size, String contentType);
        Path downloadToTempFile(String key, Path tempDirectory);
        AssetReference uploadFile(String key, Path file, String contentType);
    }

    public static final class AudioExtractionRequest {
        private final Path inputFile;
        private final Path outputFile;
        private final String audioFormat;
        private final String audioCodec;
        private final Duration timeout;

        public AudioExtractionRequest(Path inputFile, Path outputFile, String audioFormat, String audioCodec, Duration timeout) {
            this.inputFile = inputFile;
            this.outputFile = outputFile;
            this.audioFormat = audioFormat;
            this.audioCodec = audioCodec;
            this.timeout = timeout;
        }

        public Path inputFile() { return inputFile; }
        public Path outputFile() { return outputFile; }
        public String audioFormat() { return audioFormat; }
        public String audioCodec() { return audioCodec; }
        public Duration timeout() { return timeout; }
    }

    public static final class AudioExtractionResult {
        private final Path outputFile;
        private final long outputSizeBytes;
        private final String contentType;

        public AudioExtractionResult(Path outputFile, long outputSizeBytes, String contentType) {
            this.outputFile = outputFile;
            this.outputSizeBytes = outputSizeBytes;
            this.contentType = contentType;
        }

        public Path outputFile() { return outputFile; }
        public long outputSizeBytes() { return outputSizeBytes; }
        public String contentType() { return contentType; }
    }

    public interface AudioExtractionGateway { AudioExtractionResult extractAudio(AudioExtractionRequest request); }
    public interface SpeechToTextGateway { SpeechModels.SpeechToTextResult transcribe(SpeechModels.SpeechToTextRequest request); }
    public interface ScriptTranslationGateway { TranslationModels.ScriptTranslationResult translate(TranslationModels.ScriptTranslationRequest request); }
    public interface DubbingPipelineQueue { void publishStart(String jobId); }
    public interface ClockGateway { Instant now(); }
    public interface IdGeneratorGateway { String nextId(); }
}
