package com.school.school_portal.controller;

import com.school.school_portal.service.ClassService;
import com.school.school_portal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final ClassService classService;
    private final UserService userService;

    @Autowired
    public UserController(ClassService classService, UserService userService) {
        this.classService = classService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {

        String userRole = (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElse(null)).toString();

        System.out.println(userRole);

        if (userRole.equals("Student")) {

            return "redirect:/student/courses";
        }

        if (userRole.equals("Teacher")) {

            return "redirect:/teacher";
        }

        model.addAttribute("classes", classService.getClassesForCurrentUser());
        model.addAttribute("classService", classService);
        model.addAttribute("userRole", userRole);

        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }
}
