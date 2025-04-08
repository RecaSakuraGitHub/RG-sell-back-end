package com.recasakura.sellbackend.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private String timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public int getStatus() { return this.status; }
    public String getMessage() { return this.message; }
    public String getTimestamp() { return this.timestamp; }
}
