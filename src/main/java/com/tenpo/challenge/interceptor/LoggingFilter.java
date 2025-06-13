package com.tenpo.challenge.interceptor;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) response;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResp);
        chain.doFilter(request, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }
}