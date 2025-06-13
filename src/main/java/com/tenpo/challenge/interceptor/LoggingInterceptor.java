package com.tenpo.challenge.interceptor;

import com.tenpo.challenge.service.LoggerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final LoggerService loggerService;

    public LoggingInterceptor(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(response instanceof ContentCachingResponseWrapper)) {
            new ContentCachingResponseWrapper(response);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws IOException {
        if (ex == null && response instanceof ContentCachingResponseWrapper wrappedResponse && response.getStatus() < 400) {
            String endpoint = request.getRequestURI();
            String params = request.getQueryString() != null ? request.getQueryString() : "";

            String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

            if(endpoint != null && !endpoint.contains("logs")) {
                loggerService.log(endpoint, params, responseBody, null);
            }

            wrappedResponse.copyBodyToResponse();
        }
    }
}
