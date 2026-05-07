package com.clouddubber.infra.storage;

import com.clouddubber.domain.port.Ports;
import java.io.InputStream;
import org.springframework.stereotype.Component;

@Component
public class S3ObjectStorageGateway implements Ports.ObjectStorageGateway {
    public String upload(String key, InputStream in, long size, String contentType) { return key; }
}
