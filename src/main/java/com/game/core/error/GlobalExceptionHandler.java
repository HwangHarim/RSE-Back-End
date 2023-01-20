package com.game.core.error;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.dto.ErrorResponseDto;
import com.game.core.error.exception.BusinessException;
import com.game.core.error.exception.NullPointerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException exception){
        ErrorMessage errorMessage = exception.getErrorMessage();
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException exception){
        ErrorMessage errorMessage = exception.getErrorMessage();
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
