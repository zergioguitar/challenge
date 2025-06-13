package com.tenpo.challenge.controller;

import com.tenpo.challenge.model.LogResponse;
import com.tenpo.challenge.service.LoggerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logs", produces = {"application/json"})
public class LoggerController {
    private final LoggerService loggerService;

    public LoggerController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @GetMapping
    public Page<LogResponse> getLogs(Pageable pageable) {
        return loggerService.findAll(pageable).map(
                t-> new LogResponse(t.getTimestamp(),t.getEndpoint(),
                        t.getParameters(),t.getError())
        );
    }
}
