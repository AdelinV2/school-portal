package com.school.school_portal.controller;

import com.school.school_portal.dto.ClassForm;
import com.school.school_portal.entity.ClassCourse;
import com.school.school_portal.service.ClassCourseService;
import com.school.school_portal.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ClassController {

    private final ClassService classService;
    private final ClassCourseService classCourseService;

    @Autowired
    public ClassController(ClassService classService, ClassCourseService classCourseService) {
        this.classService = classService;
        this.classCourseService = classCourseService;
    }

    @GetMapping("/admin/add-class")
    public String showAddClassForm(Model model) {

        model.addAttribute("classForm", new ClassForm());
        model.addAttribute("classes", classService.getAllClassesSubjects());
        model.addAttribute("years", classService.getAllYears());
        model.addAttribute("sections", classService.getAllSections());
        model.addAttribute("tutors", classService.getAllAvailableTutors());

        return "add/add-class";
    }

    @PostMapping("/admin/add-class")
    public String addClass(@Valid ClassForm classForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("classForm", classForm);
            model.addAttribute("classes", classService.getAllClassesSubjects());
            model.addAttribute("years", classService.getAllYears());
            model.addAttribute("sections", classService.getAllSections());
            model.addAttribute("tutors", classService.getAllAvailableTutors());

            return "add/add-class";
        }

        classService.saveClass(classForm);

        return "redirect:/";
    }

    @GetMapping({"/admin/class/{id}", "/teacher/class/{id}"})
    public String getClassDetails(@PathVariable Integer id, Model model) {

        model.addAttribute("class", classService.getClassById(id));
        model.addAttribute("classCourses", classCourseService.getClassCoursesByClassId(id));

        return "class/class-info";
    }
}
