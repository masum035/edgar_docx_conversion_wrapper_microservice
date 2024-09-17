package com.wsd.edgardocswrapper.controllers;

import com.wsd.edgardocswrapper.services.IDocxPreprocessingService;
import com.wsd.edgardocswrapper.utils.ApiResponseUtil;
import com.wsd.edgardocswrapper.dto.responsesDTO.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/")
public class DocxController {

    private static final Logger logger = LoggerFactory.getLogger(DocxController.class);
    private final IDocxPreprocessingService docxPreprocessingService;

    @Autowired
    public DocxController(IDocxPreprocessingService docxPreprocessingService) {
        this.docxPreprocessingService = docxPreprocessingService;
    }

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
            docxPreprocessingService.downloadFileWithNoParams(file);
            responseDTO = ApiResponseUtil.success(null, "success", request.getRequestURI());
        } catch (Exception ex) {
            responseDTO = ApiResponseUtil.error(ex.getLocalizedMessage(), ex.getMessage(), 1001, request.getRequestURI());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}

