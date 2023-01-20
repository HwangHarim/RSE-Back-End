package com.game.core.error.exception;

import com.game.core.error.dto.ErrorMessage;

public class NullPointerException extends BusinessException{
    public NullPointerException(ErrorMessage message) {
        super(message);
    }
}
