package com.school.school_portal.controller;

import com.school.school_portal.dto.CourseForm;
import com.school.school_portal.dto.GradeForm;
import com.school.school_portal.entity.*;
import com.school.school_portal.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    private final CourseService courseService;
    private final ClassService classService;
    private final TeacherService teacherService;
    private final ClassCourseService classCourseService;
    private final StudentService studentService;
    private final AbsenceService absenceService;
    private final GradeService gradeService;

    @Autowired
    public CourseController(CourseService courseService, ClassService classService, TeacherService teacherService, ClassCourseService classCourseService, StudentService studentService, AbsenceService absenceService, GradeService gradeService) {
        this.courseService = courseService;
        this.classService = classService;
        this.teacherService = teacherService;
        this.classCourseService = classCourseService;
        this.studentService = studentService;
        this.absenceService = absenceService;
        this.gradeService = gradeService;
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

            return "add/add-course/" + id;
        }

        courseService.saveCourse(courseForm, id);

        return "redirect:/admin/class/" + id;
    }

    @GetMapping("/admin/edit-course/{id}")
    public String showEditCourseForm(@PathVariable Integer id, Model model) {

        CourseForm courseForm = new CourseForm();

        courseForm.setSubject(classCourseService.getClassCourseByCourseId(id).getCourse().getName());

        model.addAttribute("courseForm", courseForm);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("class", classCourseService.getClassByCourseId(id));

        return "update/update-course";
    }

    @PostMapping("/admin/edit-course/{id}")
    public String editCourse(@PathVariable Integer id, CourseForm courseForm, Model model) {

        courseForm.setSubject(classCourseService.getClassCourseByCourseId(id).getCourse().getName());

        if (courseForm.getTeacher().isEmpty()) {
            model.addAttribute("courseForm", courseForm);
            model.addAttribute("teachers", teacherService.getAllTeachers());
            model.addAttribute("class", classCourseService.getClassByCourseId(id));

            return "update/update-course";
        }

        courseService.updateCourse(courseForm, id);

        Integer classId = classCourseService.getClassCourseByCourseId(id).getId();

        return "redirect:/admin/class/" + classId;
    }

    @GetMapping("/admin/student/{studentId}/course/{courseId}")
    public String showStudentCourse(@PathVariable Integer studentId, @PathVariable Integer courseId, Model model) {

        Integer classCourseId = classCourseService.getClassCourseByCourseId(courseId).getId();
        Optional<Course> course = courseService.getCourseById(courseId);
        Student student = studentService.getStudentById(studentId);
        Teacher teacher = course.get().getTeacher();
        List<Absence> absences = absenceService.getUnexcusedAbsencesByStudentIdAndClassCourseId(studentId, classCourseId);
        List<Grade> grades = gradeService.getGradesByStudentIdAndClassCourseId(studentId, classCourseId);
        BigDecimal avgGrade = gradeService.getAverageGradeByStudentIdAndClassCourseId(studentId, classCourseId);

        model.addAttribute("student", student);
        model.addAttribute("course", course.get());
        model.addAttribute("teacher", teacher);
        model.addAttribute("absences", absences);
        model.addAttribute("grades", grades);
        model.addAttribute("avgGrade", avgGrade);

        return "course/course-info";
    }

    @GetMapping("/add-grade/{studentId}/{courseId}")
    public String showAddGradeForm(@PathVariable Integer studentId, @PathVariable Integer courseId, Model model) {

        model.addAttribute("grade", new GradeForm());
        model.addAttribute("student", studentService.getStudentById(studentId));
        model.addAttribute("course", courseService.getCourseById(courseId).get());
        model.addAttribute("classCourse", classCourseService.getClassCourseByCourseId(courseId));

        return "add/add-grade";
    }

    @PostMapping("/add-grade/{studentId}/{courseId}")
    public String addGrade(@PathVariable Integer studentId, @PathVariable Integer courseId, @Valid GradeForm gradeForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("grade", gradeForm);
            model.addAttribute("student", studentService.getStudentById(studentId));
            model.addAttribute("course", courseService.getCourseById(courseId).get());
            model.addAttribute("classCourse", classCourseService.getClassCourseByCourseId(courseId));

            return "add/add-grade";
        }

        gradeService.saveGrade(gradeForm);

        return "redirect:/admin/student/" + studentId + "/course/" + courseId;
    }

    @GetMapping("/edit-grade/{studentId}/{courseId}/{gradeId}")
    public String showEditGradeForm(@PathVariable Integer studentId, @PathVariable Integer courseId, @PathVariable Integer gradeId, Model model) {

        GradeForm gradeForm = new GradeForm();

        gradeForm.setGrade(gradeService.getGradeFormById(gradeId).getGrade());
        gradeForm.setDateAssigned(gradeService.getGradeFormById(gradeId).getDateAssigned());

        model.addAttribute("grade", gradeForm);
        model.addAttribute("student", studentService.getStudentById(studentId));
        model.addAttribute("course", courseService.getCourseById(courseId).get());
        model.addAttribute("classCourse", classCourseService.getClassCourseByCourseId(courseId));

        return "update/update-grade";
    }

    @PostMapping("/edit-grade/{studentId}/{courseId}/{gradeId}")
    public String editGrade(@PathVariable Integer studentId, @PathVariable Integer courseId, @PathVariable Integer gradeId, GradeForm gradeForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("grade", gradeForm);
            model.addAttribute("gradeId", gradeId);
            model.addAttribute("student", studentService.getStudentById(studentId));
            model.addAttribute("course", courseService.getCourseById(courseId).get());
            model.addAttribute("classCourse", classCourseService.getClassCourseByCourseId(courseId));

            return "update/update-grade";
        }

        gradeService.updateGrade(gradeForm, gradeId);

        return "redirect:/admin/student/" + studentId + "/course/" + courseId;
    }

    @PostMapping("/delete-grade/{studentId}/{courseId}/{gradeId}")
    public String deleteGrade(@PathVariable Integer studentId, @PathVariable Integer courseId, @PathVariable Integer gradeId) {

        gradeService.deleteGrade(gradeId);

        return "redirect:/admin/student/" + studentId + "/course/" + courseId;
    }
}
