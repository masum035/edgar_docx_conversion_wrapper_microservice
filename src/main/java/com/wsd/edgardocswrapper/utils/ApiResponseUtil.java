package com.wsd.edgardocswrapper.utils;

import com.wsd.edgardocswrapper.dto.responsesDTO.ApiResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApiResponseUtil {
    public static <T> ApiResponseDTO<T> success(T data, String message, String path) {
        ApiResponseDTO<T> responseDTO = new ApiResponseDTO<>();
        responseDTO.setSuccess(true);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        responseDTO.setErrors(null);
        responseDTO.setErrorCode(0); // No error
        responseDTO.setInstantTime(Instant.now());
        responseDTO.setPath(path);
        return responseDTO;
    }

    public static <T> ApiResponseDTO<T> error(List<String> errors, String message, int errorCode, String path) {
        ApiResponseDTO<T> responseDTO = new ApiResponseDTO<>();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(message);
        responseDTO.setData(null);
        responseDTO.setErrors(errors);
        responseDTO.setErrorCode(errorCode);
        responseDTO.setInstantTime(Instant.now());
        responseDTO.setPath(path);
        return responseDTO;
    }

    // doing method overloading
    public static <T> ApiResponseDTO<T> error(String error, String message, int errorCode, String path) {
        return error(Arrays.asList(error), message, errorCode, path);
    }
}
