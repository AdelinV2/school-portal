package com.school.school_portal.controller;

import com.school.school_portal.dto.ClassForm;
import com.school.school_portal.dto.StudentForm;
import com.school.school_portal.service.ClassCourseService;
import com.school.school_portal.service.ClassService;
import com.school.school_portal.service.StudentService;
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
    private final StudentService studentService;

    @Autowired
    public ClassController(ClassService classService, ClassCourseService classCourseService, StudentService studentService) {
        this.classService = classService;
        this.classCourseService = classCourseService;
        this.studentService = studentService;
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

    @GetMapping("/admin/class/{id}")
    public String getClassDetails(@PathVariable Integer id, Model model) {

        model.addAttribute("class", classService.getClassById(id));
        model.addAttribute("classCourses", classCourseService.getClassCoursesByClassId(id));

        return "class/class-info";
    }

    @GetMapping({"/admin/students/{classId}", "/teacher/students/{classId}"})
    public String showStudentsInClass(@PathVariable Integer classId, Model model) {

        model.addAttribute("class", classService.getClassById(classId));
        model.addAttribute("students", studentService.getStudentsByClassId(classId));

        // TODO add support for overall grade and unexcused absences and complete class-info.html

        return "class/class-students";
    }

    @GetMapping("/admin/add-student/{classId}")
    public String showAddStudentForm(@PathVariable Integer classId, Model model) {

        model.addAttribute("class", classService.getClassById(classId));
        model.addAttribute("studentForm", new StudentForm());

        return "add/add-student";
    }

    @PostMapping("/admin/add-student/{classId}")
    public String addStudent(@PathVariable Integer classId, @Valid StudentForm studentForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("class", classService.getClassById(classId));
            model.addAttribute("studentForm", studentForm);

            return "add/add-student";
        }

        if (!studentService.saveStudent(studentForm, classId)) {

            studentForm.setEmail(null);

            model.addAttribute("class", classService.getClassById(classId));
            model.addAttribute("studentForm", studentForm);
            model.addAttribute("error", "Email already exists");

            return "add/add-student";
        }

        return "redirect:/admin/students/" + classId;
    }
}
