package com.game.core.error.exception.image;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class FileConverterException extends BusinessException {

    public FileConverterException(ErrorMessage message) {
        super(message);
    }
}
