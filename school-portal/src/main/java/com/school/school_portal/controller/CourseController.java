package com.school.school_portal.controller;

import com.school.school_portal.dto.CourseForm;
import com.school.school_portal.service.ClassService;
import com.school.school_portal.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {

    private final CourseService courseService;
    private final ClassService classService;

    @Autowired
    public CourseController(CourseService courseService, ClassService classService) {
        this.courseService = courseService;
        this.classService = classService;
    }

    @GetMapping("/admin/add-course/{id}")
    public String showAddCourseForm(@PathVariable Integer id, Model model) {

        model.addAttribute("courseForm", new CourseForm());
        model.addAttribute("subjects", courseService.getAllCoursesSubjects());
        model.addAttribute("teachers", courseService.getAllTeachers());
        model.addAttribute("class", classService.getClassById(id));

        return "add/add-course";
    }

    @PostMapping("/admin/add-course/{id}")
    public String addCourse(@PathVariable Integer id, @Valid CourseForm courseForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("courseForm", courseForm);
            model.addAttribute("subjects", courseService.getAllCoursesSubjects());
            model.addAttribute("teachers", courseService.getAllTeachers());

            return "add/add-course/{id}";
        }

        courseService.saveCourse(courseForm, id);

        return "redirect:/admin/class/{id}";
    }
}
