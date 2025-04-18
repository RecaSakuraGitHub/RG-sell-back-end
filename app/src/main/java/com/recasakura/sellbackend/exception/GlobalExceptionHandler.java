package com.recasakura.sellbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
        UserAlreadyExistsException.class,
        ProductAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handlerConflictException(RuntimeException e) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler({
        UserNotFoundException.class,
        ProductNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler({
        UnLoginException.class
    })
    public ResponseEntity<ErrorResponse> handleUnLoginException(RuntimeException e) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler({
        UserPermissionDenyException.class
    })
    public ResponseEntity<ErrorResponse> handlePermissionDenyException(RuntimeException e) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
