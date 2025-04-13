package com.recasakura.sellbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserPermissionDenyException extends RuntimeException {
    public UserPermissionDenyException() {
        super("Admin access required!");
    }
}
