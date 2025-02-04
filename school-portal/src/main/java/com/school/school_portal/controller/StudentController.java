package com.school.school_portal.controller;

import com.school.school_portal.entity.Course;
import com.school.school_portal.entity.Student;
import com.school.school_portal.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final AbsenceService absenceService;
    private final GradeService gradeService;
    private final ClassCourseService classCourseService;

    public StudentController(StudentService studentService, CourseService courseService, AbsenceService absenceService, GradeService gradeService, ClassCourseService classCourseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.absenceService = absenceService;
        this.gradeService = gradeService;
        this.classCourseService = classCourseService;
    }

    @GetMapping("/courses")
    public String courses(Model model, Principal principal) {

        Student student = studentService.getStudentByEmail(principal.getName());

        model.addAttribute("courses", studentService.getCoursesByStudentId(student.getId()));

        return "course/courses";
    }

    @GetMapping("/course/{courseId}")
    public String course(@PathVariable int courseId, Model model, Principal principal) {

        Student student = studentService.getStudentByEmail(principal.getName());

        Course course = courseService.getCourseById(courseId).get();
        int classCourseId = classCourseService.getClassCourseByCourseId(courseId).getId();

        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("teacher", course.getTeacher());
        model.addAttribute("absences", absenceService.getAllAbsencesByStudentIdAndClassCourseId(student.getId(), classCourseId));
        model.addAttribute("grades", gradeService.getGradesByStudentIdAndClassCourseId(student.getId(), classCourseId));
        model.addAttribute("avgGrade", gradeService.getAverageGradeByStudentIdAndClassCourseId(student.getId(), classCourseId));

        return "course/course-info";
    }
}
