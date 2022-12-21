package com.game.rse.util.err.exception;

import com.game.rse.util.err.ErrorMessage;

public class NullPointerException extends BusinessException{
    public NullPointerException(ErrorMessage message) {
        super(message);
    }
}
