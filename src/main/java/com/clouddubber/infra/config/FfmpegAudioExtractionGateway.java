package com.clouddubber.infra.config;

import com.clouddubber.domain.port.Ports;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FfmpegAudioExtractionGateway implements Ports.AudioExtractionGateway {
    private final String ffmpegBinary;

    public FfmpegAudioExtractionGateway(@Value("${clouddubber.ffmpeg.binary-path:ffmpeg}") String ffmpegBinary) {
        this.ffmpegBinary = ffmpegBinary;
    }

    public Ports.AudioExtractionResult extractAudio(Ports.AudioExtractionRequest request) {
        List<String> cmd = List.of(ffmpegBinary, "-y", "-i", request.inputFile().toString(), "-vn", "-acodec", request.audioCodec(), request.outputFile().toString());
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean done = process.waitFor(request.timeout().toSeconds(), TimeUnit.SECONDS);
            String out = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (!done) {
                process.destroyForcibly();
                throw new IllegalStateException("ffmpeg timeout");
            }
            if (process.exitValue() != 0) throw new IllegalStateException("ffmpeg failed: " + out.substring(0, Math.min(300, out.length())));
            return new Ports.AudioExtractionResult(request.outputFile(), Files.size(request.outputFile()), "audio/mpeg");
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("audio extraction failure", e);
        }
    }
}
