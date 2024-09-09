package com.wsd.edgardocswrapper.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenericResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> GenericResponseDTO<T> empty() {
        return success(null);
    }

    public static <T> GenericResponseDTO<T> success(T data) {
        return GenericResponseDTO.<T>builder()
                .success(true)
                .message("SUCCESS!")
                .data(data)
                .build();
    }

    public static <T> GenericResponseDTO<T> error() {
        return GenericResponseDTO.<T>builder()
                .success(false)
                .message("ERROR!")
                .build();
    }

    public static <T> GenericResponseDTO<T> errorWithDetails(String msg, T data) {
        return GenericResponseDTO.<T>builder()
                .success(false)
                .message(msg)
                .data(data)
                .build();
    }
}

//User user = userService.findUserByUserName(username)
//        .orElseThrow(UserNotFoundException::new);
//UserDetailDto mappedUser = userMapper.fromUser(user);
//    return ResponseEntity.ok(GenericResponseDTO.success(mappedUser));
//return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "value").body("Ahmet Emre");