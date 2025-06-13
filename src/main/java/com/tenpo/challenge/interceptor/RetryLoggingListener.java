package com.tenpo.challenge.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component("retryLoggingListener")
public class RetryLoggingListener implements RetryListener {

    private static final Logger log = LoggerFactory.getLogger(RetryLoggingListener.class);

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("Starting retry operation");
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("Retry operation finished");
    }

    @Override
    public <T, E extends Throwable> void onError(
            RetryContext context,
            RetryCallback<T, E> callback,
            Throwable throwable) {

        int attempt = context.getRetryCount() + 1;
        log.warn("Retry attempt {} failed: {}", attempt, throwable.getMessage());
    }
}