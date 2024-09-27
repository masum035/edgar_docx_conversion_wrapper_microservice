package com.wsd.edgardocswrapper.controllers;

import com.wsd.edgardocswrapper.dto.responsesDTO.ApiResponseDTO;
import com.wsd.edgardocswrapper.serviceImplementors.PreProcesstingTesting;
import com.wsd.edgardocswrapper.utils.ApiResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/secured/")
public class SecuredController {
    private static final Logger logger = LoggerFactory.getLogger(SecuredController.class);
    private final PreProcesstingTesting mPreProcessting;

    @GetMapping("/test1")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<ApiResponseDTO<?>> HealthCheck(HttpServletRequest request) {
        logger.warn("Task performed successfully.");
        ApiResponseDTO<?> responseDTO = ApiResponseUtil.success(null, "Health Check is done", request.getRequestURI());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/test2")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<ApiResponseDTO<?>> testDotNetApi(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Extract token from Authorization header

        CompletableFuture<String> asyncAPICall = mPreProcessting.asyncAPICall(token);

//        // While waiting for the CompletableFuture, you can do other processing if needed
//        asyncAPICall.thenAccept(result -> {
//            // Handle the result when the async call is completed
//            System.out.println("Async API Call Result: " + result);
//        });

//        ApiResponseDTO<?> responseDTO = ApiResponseUtil.success(asyncAPICall, "Health Check is done", request.getRequestURI());
        ApiResponseDTO<?> responseDTO = ApiResponseUtil.success(asyncAPICall, "Health Check is done", request.getRequestURI());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
