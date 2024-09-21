package com.wsd.edgardocswrapper.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;

public interface IMinioService {
    Mono<Void> uploadFile(String bucketName, MultipartFile file);
    String generateFileName(MultipartFile file);
}
