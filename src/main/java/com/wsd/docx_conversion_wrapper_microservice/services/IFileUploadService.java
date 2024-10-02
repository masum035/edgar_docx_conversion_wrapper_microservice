package com.wsd.docx_conversion_wrapper_microservice.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface IFileUploadService {
    Mono<Void> uploadAndProcessFile(MultipartFile file);
}
