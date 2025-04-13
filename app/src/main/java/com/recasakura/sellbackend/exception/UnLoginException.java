package com.recasakura.sellbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnLoginException extends RuntimeException {
    public UnLoginException() {
        super("Please login first!");
    }
}
