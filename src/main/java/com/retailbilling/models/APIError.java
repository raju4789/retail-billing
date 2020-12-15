package com.retailbilling.models;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class APIError {

    private HttpStatus status;
    private List<String> errors;
    private LocalDateTime timestamp;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public APIError() {
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
