package com.wsd.edgardocswrapper.exceptions;

import com.wsd.edgardocswrapper.utils.ApiResponseUtil;
import com.wsd.edgardocswrapper.dto.responsesDTO.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        List<String> errors = Arrays.asList(ex.getMessage());
        ApiResponseDTO<?> apiResponseDTO = ApiResponseUtil.error(errors,ex.getMessage(), 100,request.getContextPath());
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // rest of the exceptions would be handled here

}
