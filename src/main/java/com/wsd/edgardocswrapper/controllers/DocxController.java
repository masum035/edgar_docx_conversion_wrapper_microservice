package com.wsd.edgardocswrapper.controllers;

import com.wsd.edgardocswrapper.serviceImplementors.MinioFileStorageSeviceImpl;
import com.wsd.edgardocswrapper.services.IDocxPreprocessingService;
import com.wsd.edgardocswrapper.services.IFileStrorageService;
import com.wsd.edgardocswrapper.utils.ApiResponseUtil;
import com.wsd.edgardocswrapper.dto.responsesDTO.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class DocxController {

    private static final Logger logger = LoggerFactory.getLogger(DocxController.class);
    private final IDocxPreprocessingService docxPreprocessingService;
    private final IFileStrorageService fileStorageService;


    @GetMapping("/health")
    public ResponseEntity<ApiResponseDTO<?>> HealthCheck(HttpServletRequest request) {
        logger.warn("Task performed successfully.");
        ApiResponseDTO<?> responseDTO = ApiResponseUtil.success(null, "Health Check is done", request.getRequestURI());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDTO<?>> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResponseDTO<?> responseDTO;
        try {
            String fileUrl = fileStorageService.uploadFile(file);
            System.out.println(fileUrl);
            docxPreprocessingService.downloadFileWithNoParams(file);
            responseDTO = ApiResponseUtil.success(null, "success", request.getRequestURI());
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception ex) {
            responseDTO = ApiResponseUtil.error(ex.getLocalizedMessage(), ex.getMessage(), 1001, request.getRequestURI());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

