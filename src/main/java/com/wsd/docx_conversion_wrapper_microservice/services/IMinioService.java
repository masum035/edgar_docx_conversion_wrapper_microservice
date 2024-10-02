package com.wsd.docx_conversion_wrapper_microservice.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface IMinioService {
    Mono<Void> uploadFile(String bucketName, MultipartFile file);
    String generateFileName(MultipartFile file);
}
