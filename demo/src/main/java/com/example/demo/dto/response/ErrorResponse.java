package com.example.demo.dto.response;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private List<String> reason;

    private String suggestion;

    private Instant timestamp;

    private String codeType;

    public static ErrorResponse of(List<String> reason, String suggestion, String codeType) {
        return ErrorResponse.builder()
                .reason(reason)
                .suggestion(suggestion)
                .timestamp(Instant.now())
                .codeType(codeType)
                .build();
    }
}