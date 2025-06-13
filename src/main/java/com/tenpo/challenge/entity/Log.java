package com.tenpo.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String endpoint;
    private String parameters;
    private String response;
    private String error;

    public Long getId() { return id; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndpoint() { return endpoint; }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getParameters() { return parameters; }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getResponse() { return response; }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() { return error; }

    public void setError(String error) {
        this.error = error;
    }
}