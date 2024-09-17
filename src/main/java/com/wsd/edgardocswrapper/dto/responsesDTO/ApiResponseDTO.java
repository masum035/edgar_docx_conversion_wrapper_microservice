package com.wsd.edgardocswrapper.dto.responsesDTO;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private int errorCode; // for custom error codes for project specific
    private Instant instantTime;  // Adding timestamp for tracking in UTC
    private String path;     // Adding path to identify the API endpoint
}