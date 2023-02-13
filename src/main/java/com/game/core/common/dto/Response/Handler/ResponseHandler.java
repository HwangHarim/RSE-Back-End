package com.game.core.common.dto.Response.Handler;

import com.game.core.common.dto.Response.ResponseMessage;
import com.game.core.common.dto.Response.ResponseDto;
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
