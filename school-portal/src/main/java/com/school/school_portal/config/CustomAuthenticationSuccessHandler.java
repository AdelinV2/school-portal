package com.school.school_portal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "";

        for (GrantedAuthority authority : authorities) {

            if (authority.getAuthority().equals("Admin")) {

                redirectUrl = "/";

                break;
            } else if (authority.getAuthority().equals("Teacher")) {

                redirectUrl = "/";

                break;
            } else if (authority.getAuthority().equals("Student")) {

                redirectUrl = "/";

                break;
            }
        }

        if (!redirectUrl.isEmpty()) {
            response.sendRedirect(redirectUrl);
        } else {
            throw new IllegalStateException("User role not found");
        }
    }
}