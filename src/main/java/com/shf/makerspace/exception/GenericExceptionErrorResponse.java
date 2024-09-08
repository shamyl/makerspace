package com.shf.makerspace.exception;

import java.time.LocalDateTime;

public class GenericExceptionErrorResponse {
    private String message;
    private String path;
    private int status;
    private String error;
    private LocalDateTime timestamp;

    public GenericExceptionErrorResponse(String message, String path, int status, String error, LocalDateTime timestamp) {
        this.message = message;
        this.path = path;
        this.status = status;
        this.error = error;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
