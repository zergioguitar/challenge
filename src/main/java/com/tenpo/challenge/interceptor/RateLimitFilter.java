package com.tenpo.challenge.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(3)
                                .refillGreedy(3,Duration.ofMinutes(1))
                                .build()
                        ).build();
    }

    private Bucket resolveBucket(String ip) {
        return cache.computeIfAbsent(ip, k -> createNewBucket());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String ip = httpReq.getRemoteAddr();
        if(!httpReq.getRequestURI().contains("swagger")) {
            Bucket bucket = resolveBucket(ip);

            if (bucket.tryConsume(1)) {
                chain.doFilter(request, response);
            } else {
                httpResp.setStatus(429);
                httpResp.setContentType("application/json");
                String json = String.format("""
                        {"path":"%s","message":"You have reached the maximum number of requests per Second","error":"Too Many Requests","timestamp":"%s","status":429}
                        """, httpReq.getRequestURI(), LocalDateTime.now());
                httpResp.getWriter().write(json);
            }
        }
    }
}