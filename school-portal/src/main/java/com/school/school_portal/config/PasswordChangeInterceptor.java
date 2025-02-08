package com.school.school_portal.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PasswordChangeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Boolean mustChangePassword = (Boolean) request.getSession().getAttribute("mustChangePassword");
        String uri = request.getRequestURI();

        if (uri.startsWith("/assets")) {
            return true;
        }

        if (mustChangePassword != null && mustChangePassword && !request.getRequestURI().equals("/change-password")) {
            response.sendRedirect("/change-password");
            return false;
        }

        return true;
    }
}