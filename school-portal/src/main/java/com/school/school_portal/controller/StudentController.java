package com.school.school_portal.controller;

import com.school.school_portal.entity.Course;
import com.school.school_portal.entity.Student;
import com.school.school_portal.entity.User;
import com.school.school_portal.service.ClassCourseService;
import com.school.school_portal.service.CourseService;
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

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/courses")
    public String courses(Model model, Principal principal) {

        Student student = studentService.getStudentByEmail(principal.getName());

        model.addAttribute("courses", studentService.getCoursesByStudentId(student.getId()));

        return "course/courses";
    }
}
