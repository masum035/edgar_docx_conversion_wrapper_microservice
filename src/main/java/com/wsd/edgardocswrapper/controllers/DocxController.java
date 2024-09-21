package com.wsd.edgardocswrapper.controllers;

import com.wsd.edgardocswrapper.services.IDocxPreprocessingService;
import com.wsd.edgardocswrapper.services.IFileUploadService;
import com.wsd.edgardocswrapper.services.IMinioService;
import com.wsd.edgardocswrapper.utils.ApiResponseUtil;
import com.wsd.edgardocswrapper.dto.responsesDTO.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class DocxController {

    private static final Logger logger = LoggerFactory.getLogger(DocxController.class);
    private final IDocxPreprocessingService docxPreprocessingService;
    private final IMinioService minioService;
    private final IFileUploadService fileUploadService;


    @GetMapping("/health")
    public ResponseEntity<ApiResponseDTO<?>> HealthCheck(HttpServletRequest request) {
        logger.warn("Task performed successfully.");
        ApiResponseDTO<?> responseDTO = ApiResponseUtil.success(null, "Health Check is done", request.getRequestURI());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/upload2")
    public Mono<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileUploadService.uploadAndProcessFile(file)
                .then(minioService.uploadFile("original-files",file))
                .then(Mono.just(ResponseEntity.ok("File uploaded, processed, and stored in MinIO successfully.")))
                .onErrorResume(error -> {
                    return Mono.just(ResponseEntity.status(500).body("An error occurred: " + error.getMessage()));
                });
    }
    
    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDTO<?>> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResponseDTO<?> responseDTO;
        try {
            minioService.uploadFile("original-files",file);
//            System.out.println(fileUrl);
            docxPreprocessingService.downloadFileWithNoParams(file);
            responseDTO = ApiResponseUtil.success(null, "success", request.getRequestURI());
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception ex) {
            responseDTO = ApiResponseUtil.error(ex.getLocalizedMessage(), ex.getMessage(), 1001, request.getRequestURI());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        File file = File.createTempFile("temp", multipartFile.getOriginalFilename());

        try (InputStream inputStream = multipartFile.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

        return file;
    }
}

