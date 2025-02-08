package com.school.school_portal.controller;

import com.school.school_portal.entity.User;
import com.school.school_portal.repository.UserRepository;
import com.school.school_portal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;

    @Autowired
    public GlobalControllerAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void addAttributes(Model model) {

        User currentUser = getCurrentUser();

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
        }
    }

    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            return userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        }

        return null;
    }
}
