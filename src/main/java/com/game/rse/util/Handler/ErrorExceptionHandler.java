package com.game.rse.util.Handler;

import com.game.rse.util.err.ErrorMessage;
import com.game.rse.util.err.ErrorResponseDto;
import com.game.rse.util.err.exception.BusinessException;
import com.game.rse.util.err.exception.NullPointerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorExceptionHandler {

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
