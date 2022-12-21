package com.game.rse.util.err;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorResponseDto {
    private String message;
    private final LocalDateTime serverDateTime;

    public ErrorResponseDto(ErrorMessage errorMessage) {
        this.message = errorMessage.name();
        this.serverDateTime = LocalDateTime.now();
    }

    public static ErrorResponseDto of(ErrorMessage errorMessage) {
        return new ErrorResponseDto(errorMessage);
    }
}