package com.tenpo.challenge.gateway;

import org.springframework.retry.annotation.Retryable;

public interface PercentageClient {
    @Retryable(maxAttempts = 3, listeners = {"retryLoggingListener"})
    double getPercentage();
}
