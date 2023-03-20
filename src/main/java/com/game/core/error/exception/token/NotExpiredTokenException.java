package com.game.core.error.exception.token;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class NotExpiredTokenException extends BusinessException {

    public NotExpiredTokenException(ErrorMessage message) {
        super(message);
    }
}
