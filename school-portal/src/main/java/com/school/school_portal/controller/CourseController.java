package com.school.school_portal.controller;

import com.school.school_portal.dto.CourseForm;
import com.school.school_portal.service.ClassCourseService;
import com.school.school_portal.service.ClassService;
import com.school.school_portal.service.CourseService;
import com.school.school_portal.service.TeacherService;
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
    private final TeacherService teacherService;
    private final ClassCourseService classCourseService;

    @Autowired
    public CourseController(CourseService courseService, ClassService classService, TeacherService teacherService, ClassCourseService classCourseService) {
        this.courseService = courseService;
        this.classService = classService;
        this.teacherService = teacherService;
        this.classCourseService = classCourseService;
    }

    @GetMapping("/admin/add-course/{id}")
    public String showAddCourseForm(@PathVariable Integer id, Model model) {

        model.addAttribute("courseForm", new CourseForm());
        model.addAttribute("subjects", courseService.getAllCoursesSubjects());
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("class", classService.getClassById(id));

        return "add/add-course";
    }

    @PostMapping("/admin/add-course/{id}")
    public String addCourse(@PathVariable Integer id, @Valid CourseForm courseForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("courseForm", courseForm);
            model.addAttribute("subjects", courseService.getAllCoursesSubjects());
            model.addAttribute("teachers", teacherService.getAllTeachers());

            return "add/add-course/{id}";
        }

        courseService.saveCourse(courseForm, id);

        return "redirect:/admin/class/{id}";
    }

    @GetMapping("/admin/edit-course/{id}")
    public String showEditCourseForm(@PathVariable Integer id, CourseForm courseForm, Model model) {

        model.addAttribute("courseForm", courseForm);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("class", classCourseService.getClassByCourseId(id));

        return "update/update-course";
    }

    @PostMapping("/admin/edit-course/{id}")
    public String editCourse(@PathVariable Integer id, @Valid CourseForm courseForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("courseForm", courseForm);
            model.addAttribute("teachers", teacherService.getAllTeachers());

            return "update/update-course";
        }

        courseService.updateCourse(courseForm, id);

        Integer classId = classCourseService.getClassCourseByCourseId(id).getId();

        return "redirect:/admin/class/{classId}";
    }
}
