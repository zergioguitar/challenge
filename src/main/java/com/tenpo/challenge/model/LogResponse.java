package com.tenpo.challenge.model;

import java.time.LocalDateTime;

public class LogResponse {
    private LocalDateTime timestamp;
    private String endpoint;
    private String parameters;
    private String error;

    public LogResponse(LocalDateTime timestamp, String endpoint, String parameters, String error) {
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.parameters = parameters;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
