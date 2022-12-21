package com.game.rse.util.err.exception;

public class BadRequestException extends Throwable {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
