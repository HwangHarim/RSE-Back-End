package com.game.core.error.exception.token;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class TokenValidFailedException extends BusinessException {

    public TokenValidFailedException(ErrorMessage message) {
        super(message);
    }
}