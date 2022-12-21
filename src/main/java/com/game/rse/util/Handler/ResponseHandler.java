package com.game.rse.util.Handler;

import com.game.rse.util.ResponseDto.ResponseDto;
import com.game.rse.util.ResponseDto.ResponseMessage;
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
