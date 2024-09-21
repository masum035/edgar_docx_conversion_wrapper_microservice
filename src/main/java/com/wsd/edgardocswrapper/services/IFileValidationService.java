package com.wsd.edgardocswrapper.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileValidationService {
    boolean isValidFile(MultipartFile file);
}
