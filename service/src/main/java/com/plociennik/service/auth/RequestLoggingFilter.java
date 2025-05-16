package com.plociennik.service.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

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
            logRequestDetails(httpRequest, fullUrl);
        }

        chain.doFilter(request, response);
    }

    private void logRequestDetails(HttpServletRequest request, String fullUrl) {
        logger.info("----- Incoming Request -----");
        logger.info("Full URL: {}", fullUrl);
        logger.info("Method: {}", request.getMethod());
        logger.info("URI: {}", request.getRequestURI());
        logger.info("Servlet Path: {}", request.getServletPath());
        logger.info("Remote Addr: {}", request.getRemoteAddr());

        logger.info("Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            Collections.list(headerNames).forEach(name ->
                    logger.info("  {}: {}", name, request.getHeader(name)));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("Authenticated user: {}", authentication.getName());
            logger.info("Authorities: {}", authentication.getAuthorities());
            logger.info("Principal: {}", authentication.getPrincipal());
        } else {
            logger.info("No authentication found in SecurityContext");
        }

        logger.info("----------------------------");
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
