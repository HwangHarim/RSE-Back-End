package com.game.core.common.response.handler;

import com.game.core.common.response.dto.ResponseDto;
import com.game.core.common.response.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    public <T> ResponseEntity<ResponseDto> toResponseEntity(ResponseMessage message, T data) {
        return ResponseEntity
            .status(message.getStatus())
            .body(
                new ResponseDto<>(message, data)
            );
    }
}
