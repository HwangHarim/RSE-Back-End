package com.game.core.error.exception.oauth;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class NoAuthException extends BusinessException {
    public NoAuthException(ErrorMessage message) {
        super(message);
    }
}
