package com.wsd.edgardocswrapper.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFileUploadService {
    Mono<Void> uploadAndProcessFile(MultipartFile file);
}
