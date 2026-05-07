package com.clouddubber.infra.storage;

import com.clouddubber.domain.port.Ports;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class S3ObjectStorageGateway implements Ports.ObjectStorageGateway {
    public String upload(String key, InputStream in, long size, String contentType) { return key; }
    public Path downloadToTempFile(String key, Path tempDirectory) {
        try {
            Path f = Files.createTempFile(tempDirectory, "src-", ".bin");
            Files.writeString(f, "video");
            return f;
        } catch (IOException e) { throw new IllegalStateException(e); }
    }
    public Ports.AssetReference uploadFile(String key, Path file, String contentType) { return new Ports.AssetReference(key, contentType); }
}
