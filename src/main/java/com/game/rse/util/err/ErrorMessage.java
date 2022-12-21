package com.game.rse.util.err;

import java.util.Arrays;
import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    NOT_FIND_ID_BOARD(HttpStatus.BAD_REQUEST, "요청한 보드가 존재하지 않습니다."),
    NOT_FIND_ID_USER(HttpStatus.BAD_REQUEST, "요한한 사용자가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus status(){
        return status;
    }

    public String message(){
        return message;
    }

    public static ErrorMessage of(String errorMessage){
        return Arrays.stream(values())
            .filter(e -> e.message.equals(errorMessage))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Non Existent Exception"));
    }
}