package com.game.core.common.response.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private final String message;
    private final String serverDateTime;
    private final T data;

    public ResponseDto(ResponseMessage message, T data) {
        this.message = message.name();
        this.serverDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.data = data;
    }
}