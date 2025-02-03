package com.school.school_portal.service;

import com.school.school_portal.dto.StudentForm;
import com.school.school_portal.entity.*;
import com.school.school_portal.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final ClassService classService;
    private final CourseService courseService;
    private final ClassCourseService classCourseService;

    @Autowired
    public StudentService(StudentRepository studentRepository, UserService userService, ClassService classService, CourseService courseService, ClassCourseService classCourseService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.classService = classService;
        this.courseService = courseService;
        this.classCourseService = classCourseService;
    }

    public List<Student> getStudentsByClassId(Integer classId) {
        return studentRepository.findAllByClassField_Id(classId);
    }

    public boolean saveStudent(StudentForm studentForm, Integer classId) {

        User newUser = new User();
        Student newStudent = new Student();

        newUser.setFirstName(studentForm.getFirstName());
        newUser.setLastName(studentForm.getLastName());
        newUser.setEmail(studentForm.getEmail());
        newUser.setActive(false);
        newUser.setRole(new Role(3, "Student"));

        if(!userService.saveNewUserWithRandomPassword(newUser)) {
            return false;
        }

        newStudent.setUser(newUser);
        newStudent.setClassField(classService.getClassById(classId));

        studentRepository.save(newStudent);

        return true;
    }

    public Student getStudentById(Integer studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public void updateStudent(Integer studentId, @Valid StudentForm studentForm) {

        Student student = studentRepository.findById(studentId).orElse(null);

        if(student == null) {
            return;
        }

        User user = student.getUser();

        user.setFirstName(studentForm.getFirstName());
        user.setLastName(studentForm.getLastName());
        user.setEmail(studentForm.getEmail());

        userService.updateUser(user);
    }

    public void deleteStudent(Integer studentId) {

        userService.deleteUser(studentRepository.findById(studentId).orElse(null).getUser().getId());
    }

    public List<Course> getCoursesByStudentId(Integer studentId) {

        Integer classId = studentRepository.findById(studentId).orElse(null).getClassField().getId();

        return courseService.getCoursesByClassId(classId);
    }

    public Student getStudentByEmail(String name) {

        return studentRepository.findByUser_Email(name);
    }
}
