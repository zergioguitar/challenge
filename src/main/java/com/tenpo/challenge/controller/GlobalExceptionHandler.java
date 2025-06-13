package com.tenpo.challenge.controller;

import com.tenpo.challenge.service.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final LoggerService loggerService;

    public GlobalExceptionHandler(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    private ResponseEntity<Object> buildErrorResponse(HttpServletRequest request, HttpStatus status, String message) {
        String path = request.getRequestURI();
        String query = request.getQueryString();
        String params = query != null ? query : "";

        loggerService.log(path, params, null, message);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("path", path);
        body.put("message", message);
        body.put("error", status.getReasonPhrase());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            org.springframework.web.bind.MissingServletRequestParameterException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(com.tenpo.challenge.controller.exception.ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailable(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(request, HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }
}
