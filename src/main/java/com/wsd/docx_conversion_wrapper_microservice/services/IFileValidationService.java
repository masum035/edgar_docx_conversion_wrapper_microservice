package com.wsd.docx_conversion_wrapper_microservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileValidationService {
    boolean isValidFile(MultipartFile file);
}
