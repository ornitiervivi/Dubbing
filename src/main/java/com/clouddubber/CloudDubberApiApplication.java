package com.clouddubber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudDubberApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudDubberApiApplication.class, args);
    }
}
