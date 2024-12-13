package com.school.school_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/add-class")
    public String addClass() {
        return "add/add-class";
    }

    @RequestMapping("/add-student")
    public String addStudent() {
        return "add/add-student";
    }

    @RequestMapping("/add-teacher")
    public String addTeacher() {
        return "add/add-teacher";
    }

    @RequestMapping("/add-grade")
    public String addGrade() {
        return "add/add-grade";
    }

    @RequestMapping("/update-course")
    public String updateCourse() {
        return "update/update-course";
    }

    @RequestMapping("/update-student")
    public String updateStudent() {
        return "update/update-student";
    }

    @RequestMapping("/update-teacher")
    public String updateTeacher() {
        return "update/update-teacher";
    }

    @RequestMapping("/update-grade")
    public String updateGrade() {
        return "update/update-grade";
    }


}
