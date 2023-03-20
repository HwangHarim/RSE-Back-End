package com.game.core.error;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.dto.ErrorResponseDto;
import com.game.core.error.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException exception){
        ErrorMessage errorMessage = exception.getErrorMessage();
        return ErrorResponseDto.of(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException() {
        return ErrorResponseDto.of(ErrorMessage.INTERNAL_SERVER_ERROR);
    }
}