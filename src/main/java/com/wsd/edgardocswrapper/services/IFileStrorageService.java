package com.wsd.edgardocswrapper.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStrorageService {
    String uploadFile(MultipartFile file);
    String generateFileName(MultipartFile file);
}
