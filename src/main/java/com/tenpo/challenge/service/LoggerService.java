package com.tenpo.challenge.service;

import com.tenpo.challenge.entity.Log;
import com.tenpo.challenge.repository.ApiCallLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggerService {
    private final ApiCallLogRepository repository;

    public LoggerService(ApiCallLogRepository repository) {
        this.repository = repository;
    }

    @Async
    public void log(String endpoint, String parameters, String response, String error) {
        try {
            Log log = new Log();
            log.setTimestamp(LocalDateTime.now());
            log.setEndpoint(endpoint);
            log.setParameters(parameters);
            log.setResponse(response);
            log.setError(error);

            repository.save(log);
        } catch (Exception ignored) {
        }
    }

    public Page<Log> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
