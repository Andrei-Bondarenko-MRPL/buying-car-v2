package com.example.buyingcarv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchOrderExistsException.class)
    public ResponseEntity<String> handleNoSuchExistsException(NoSuchOrderExistsException exp) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exp.getMessage());
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<String> handleOrderAlreadyExistsException(OrderAlreadyExistsException exp) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exp.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception exp) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Unexpected server error",
                LocalDateTime.now().toString()
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid input",
                LocalDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
