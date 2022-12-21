package com.game.rse.util.err.exception;

import com.game.rse.util.err.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage message){
        super(message.message());
        this.errorMessage = message;
    }
}