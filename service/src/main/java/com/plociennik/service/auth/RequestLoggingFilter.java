package com.plociennik.service.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("unused")
public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String fullUrl = httpRequest.getRequestURL().toString();

        if (httpRequest.getQueryString() != null) {
            fullUrl += "?" + httpRequest.getQueryString();
        }

        if (!isRequestKeepAlive(fullUrl)) {
            logIncomingRequest(fullUrl, httpRequest);
        }

        chain.doFilter(request, response);
    }

    private void logIncomingRequest(String fullUrl, HttpServletRequest httpRequest) {
        logger.info("Incoming request URL: {}", fullUrl);
        logger.info("Request URI: {}", httpRequest.getRequestURI());
        logger.info("Servlet Path: {}", httpRequest.getServletPath());
    }

    private boolean isRequestKeepAlive(String fullUrl) {
        String keepAliveSubstring = "/keep-alive";
        return fullUrl.contains(keepAliveSubstring);
    }
}
