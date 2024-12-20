package com.school.school_portal.service;

import com.school.school_portal.dto.StudentForm;
import com.school.school_portal.entity.Role;
import com.school.school_portal.entity.Student;
import com.school.school_portal.entity.User;
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

    @Autowired
    public StudentService(StudentRepository studentRepository, UserService userService, ClassService classService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.classService = classService;
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
}
