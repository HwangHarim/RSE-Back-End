package com.game.core.error.exception.member;

import com.game.core.error.dto.ErrorMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NotFoundUserException extends UsernameNotFoundException {

    private ErrorMessage errorMessage;

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(ErrorMessage message) {
        super(message.getMessage());
        this.errorMessage = message;
    }
}
