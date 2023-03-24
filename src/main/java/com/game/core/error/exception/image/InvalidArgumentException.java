package com.game.core.error.exception.image;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class InvalidArgumentException extends BusinessException {

    public InvalidArgumentException(ErrorMessage message) {
        super(message);
    }
}
