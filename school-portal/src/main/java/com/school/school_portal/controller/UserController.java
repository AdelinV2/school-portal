package com.school.school_portal.controller;

import com.school.school_portal.service.ClassService;
import com.school.school_portal.service.UserService;
import com.school.school_portal.util.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        if (userRole.equals("Student")) {

            return "redirect:/student/courses";
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

    @GetMapping("/change-password")
    public String showResetPassword() {

        return "reset-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request,
                                 Model model) {

        userService.changePassword(userDetails.getUsername(), newPassword);

        request.getSession().removeAttribute("mustChangePassword");
        model.addAttribute("message", "Password changed successfully");

        return "login";
    }

    @PostMapping("/reset-password/{studentId}")
    public String resetPassword(@PathVariable("studentId") Integer studentId, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        userService.resetPassword(userService.getUserByStudentId(studentId).getEmail());

        redirectAttributes.addFlashAttribute("message", "Password reset successfully");

        return "redirect:/admin/edit-student/" + studentId;
    }
}
