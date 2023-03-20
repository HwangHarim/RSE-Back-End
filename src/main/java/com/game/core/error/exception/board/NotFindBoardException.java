package com.game.core.error.exception.board;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class NotFindBoardException extends BusinessException {
    public NotFindBoardException(ErrorMessage message) {
        super(message);
    }
}
