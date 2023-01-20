package com.game.core.error.exception;

public class BadRequestException extends Throwable {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
