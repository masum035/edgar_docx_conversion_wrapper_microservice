package com.wsd.edgardocswrapper.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EdgarAppConfig {

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.secret.key}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;
    
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    // Bean for object storage with minio access
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    // Bean for micrometer tracing and @Observed annotation
    @Bean
    ObservedAspect observedAspect(ObservationRegistry registry) {
        return new ObservedAspect(registry);
    }
}
