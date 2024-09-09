package com.wsd.edgardocswrapper.exceptions;

import com.wsd.edgardocswrapper.dto.responses.GenericResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        GenericResponseDTO<String> errorWithDetails = GenericResponseDTO.errorWithDetails(ex.getMessage(), request.getDescription(true));
        return new ResponseEntity<>(errorWithDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
