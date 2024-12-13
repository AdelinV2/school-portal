package com.school.school_portal.controller;

import com.school.school_portal.dto.TeacherForm;
import com.school.school_portal.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/admin/add-teacher")
    public String showAddTeacherForm(Model model) {

        model.addAttribute("teacherForm", new TeacherForm());

        return "add/add-teacher";
    }

    @PostMapping("/admin/add-teacher")
    public String addTeacher(@Valid TeacherForm teacherForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("teacherForm", teacherForm);
            return "add/add-teacher";
        }

        return "redirect:/";
    }
}
