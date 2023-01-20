package com.game.core.common.util.Handler;

import com.game.core.common.dto.ResponseMessage;
import com.game.core.common.dto.ResponseDto;
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
