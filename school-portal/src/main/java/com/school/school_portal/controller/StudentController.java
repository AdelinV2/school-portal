package com.school.school_portal.controller;

import com.school.school_portal.entity.User;
import com.school.school_portal.service.StudentService;
import com.school.school_portal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping("/courses")
    public String courses(Model model, Principal principal) {

        model.addAttribute("student", studentService.getStudentByEmail(principal.getName()));

        return "course/courses";
    }
}
