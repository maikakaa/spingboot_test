package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the response
public class ApiResponse<T> {

    private int code;

    private boolean success;

    private String message;

    private T result;

    private ErrorResponse error;

    public static <T> ApiResponse<T> success(String message, T result) {
        return ApiResponse.<T>builder()
                .code(200)
                .success(true)
                .message(message)
                .result(result)
                .build();
    }

    public static ApiResponse<Void> error(int code, String message, ErrorResponse error) {
        return ApiResponse.<Void>builder()
                .code(code)
                .success(false)
                .message(message)
                .error(error)
                .build();
    }
}