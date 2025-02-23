package com.school.school_portal.controller;

import com.school.school_portal.dto.ClassForm;
import com.school.school_portal.dto.StudentForm;
import com.school.school_portal.entity.ClassCourse;
import com.school.school_portal.entity.Grade;
import com.school.school_portal.entity.Student;
import com.school.school_portal.entity.Teacher;
import com.school.school_portal.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ClassController {

    private final ClassService classService;
    private final ClassCourseService classCourseService;
    private final StudentService studentService;
    private final GradeService gradeService;
    private final AbsenceService absenceService;
    private final TeacherService teacherService;

    @Autowired
    public ClassController(ClassService classService, ClassCourseService classCourseService, StudentService studentService, GradeService gradeService, AbsenceService absenceService, TeacherService teacherService) {
        this.classService = classService;
        this.classCourseService = classCourseService;
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.absenceService = absenceService;
        this.teacherService = teacherService;
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

    @GetMapping("/teacher/class/{classId}")
    public String getTeacherClassDetails(@PathVariable Integer classId, Model model) {

        Teacher teacher = teacherService.getTeacherByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        if (teacher == null) {
            return "redirect:/";
        }

        ClassCourse classCourse = classCourseService.getClassCourseByClassIdAndTeacherId(classId, teacher.getId());

        Map<Integer, BigDecimal> overallGrades = new HashMap<>();
        Map<Integer, Long> unexcusedAbsences = new HashMap<>();

        for (Student student : studentService.getStudentsByClassId(classId)) {
            overallGrades.put(student.getId(), gradeService.getAverageGradeByStudentIdAndClassCourseId(student.getId(), classCourse.getId()));
            unexcusedAbsences.put(student.getId(), (long) absenceService.getTotalUnexcusedAbsencesByStudentId(student.getId()).size());
        }

        model.addAttribute("class", classService.getClassById(classId));
        model.addAttribute("classCourse", classCourse);
        model.addAttribute("class", classService.getClassById(classId));
        model.addAttribute("students", studentService.getStudentsByClassId(classId));
        model.addAttribute("overallGrades", overallGrades);
        model.addAttribute("unexcusedAbsences", unexcusedAbsences);

        return "class/class-students";
    }

    @GetMapping("/admin/students/{classId}")
    public String showStudentsInClass(@PathVariable Integer classId, Model model) {

        Map<Integer, BigDecimal> overallGrades = new HashMap<>();
        Map<Integer, Long> unexcusedAbsences = new HashMap<>();

        for (Student student : studentService.getStudentsByClassId(classId)) {
            overallGrades.put(student.getId(), gradeService.getTotalAverageGradeByStudentId(student.getId()));
            unexcusedAbsences.put(student.getId(), (long) absenceService.getTotalUnexcusedAbsencesByStudentId(student.getId()).size());
        }

        model.addAttribute("class", classService.getClassById(classId));
        model.addAttribute("students", studentService.getStudentsByClassId(classId));
        model.addAttribute("overallGrades", overallGrades);
        model.addAttribute("unexcusedAbsences", unexcusedAbsences);

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

    @GetMapping("/admin/edit-student/{studentId}")
    public String showEditStudentForm(@PathVariable Integer studentId, Model model) {

        StudentForm studentForm = new StudentForm();

        studentForm.setFirstName(studentService.getStudentById(studentId).getUser().getFirstName());
        studentForm.setLastName(studentService.getStudentById(studentId).getUser().getLastName());
        studentForm.setEmail(studentService.getStudentById(studentId).getUser().getEmail());

        model.addAttribute("class", studentService.getStudentById(studentId).getClassField());
        model.addAttribute("studentForm", studentForm);

        return "update/update-student";
    }

    @PostMapping("/admin/edit-student/{studentId}")
    public String editStudent(@PathVariable Integer studentId, @Valid StudentForm studentForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("class", studentService.getStudentById(studentId).getClassField());
            model.addAttribute("studentForm", studentForm);

            return "update/update-student";
        }

        studentService.updateStudent(studentId, studentForm);

        return "redirect:/admin/students/" + studentService.getStudentById(studentId).getClassField().getId();
    }

    @GetMapping("/admin/student/{studentId}")
    public String showStudentDetails(@PathVariable Integer studentId, Model model) {

        Student student = studentService.getStudentById(studentId);
        Integer classId = student.getClassField().getId();

        Map<Integer, Long> unexcusedAbsences = new HashMap<>();
        Map<Integer, Long> excusedAbsences = new HashMap<>();
        Map<Integer, BigDecimal> overallGrades = new HashMap<>();
        Map<Integer, List<Grade>> grades = new HashMap<>();

        for (ClassCourse classCourse : classCourseService.getClassCoursesByClassId(classId)) {

            Integer classCourseId = classCourse.getId();
            unexcusedAbsences.put(classCourseId, (long) absenceService.getUnexcusedAbsencesByStudentIdAndClassCourseId(studentId, classCourseId).size());
            excusedAbsences.put(classCourseId, (long) absenceService.getExcusedAbsencesByStudentIdAndClassCourseId(studentId, classCourseId).size());
            overallGrades.put(classCourseId, gradeService.getAverageGradeByStudentIdAndClassCourseId(studentId, classCourseId));
            grades.put(classCourseId, gradeService.getGradesByStudentIdAndClassCourseId(studentId, classCourseId));
        }

        model.addAttribute("courses", classCourseService.getClassCoursesByClassId(classId));
        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        model.addAttribute("unexcusedAbsences", unexcusedAbsences);
        model.addAttribute("excusedAbsences", excusedAbsences);
        model.addAttribute("overallGrades", overallGrades);

        return "student/student-info";
    }

    @PostMapping("/admin/delete-student/{studentId}")
    public String deleteStudent(@PathVariable Integer studentId) {

        Integer classId = studentService.getStudentById(studentId).getClassField().getId();
        studentService.deleteStudent(studentId);

        return "redirect:/admin/students/" + classId;
    }
}
