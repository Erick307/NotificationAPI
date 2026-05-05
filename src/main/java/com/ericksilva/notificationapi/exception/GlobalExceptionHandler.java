package com.ericksilva.notificationapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception){
        String message = Objects.requireNonNull(exception
                        .getBindingResult()
                        .getFieldError())
                .getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(400, message);
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(){
        ErrorResponse errorResponse = new ErrorResponse(500,"Internal server error");
        return ResponseEntity.status(500).body(errorResponse);
    }
}