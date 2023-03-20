package com.game.core.error.exception.member;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class DuplicateUserException extends BusinessException {

    public DuplicateUserException(ErrorMessage message) {
        super(message);
    }
}
