package com.wsd.docx_conversion_wrapper_microservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface IDocxPreprocessingService {
    void downloadFileWithNoParams(MultipartFile file);
}
