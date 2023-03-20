package com.game.core.error.exception.token;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class InvalidAccessTokenException extends BusinessException {

    public InvalidAccessTokenException(ErrorMessage message) {
        super(message);
    }
}
