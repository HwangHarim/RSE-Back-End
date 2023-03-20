package com.game.core.error.exception.token;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class InvalidRefreshTokenException extends BusinessException {

    public InvalidRefreshTokenException(ErrorMessage message) {
        super(message);
    }
}
