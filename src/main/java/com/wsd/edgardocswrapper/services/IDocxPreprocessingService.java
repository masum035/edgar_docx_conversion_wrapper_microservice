package com.wsd.edgardocswrapper.services;

import org.springframework.web.multipart.MultipartFile;

public interface IDocxPreprocessingService {
    void downloadFileWithNoParams(MultipartFile file);
}
