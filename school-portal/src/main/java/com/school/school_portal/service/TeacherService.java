package com.school.school_portal.service;

import com.school.school_portal.dto.TeacherForm;
import com.school.school_portal.entity.Role;
import com.school.school_portal.entity.Teacher;
import com.school.school_portal.entity.User;
import com.school.school_portal.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserService userService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, UserService userService) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
    }

    public boolean saveTeacher(TeacherForm teacherForm) {

        User newUser = new User();
        Teacher newTeacher = new Teacher();

        newUser.setFirstName(teacherForm.getFirstName());
        newUser.setLastName(teacherForm.getLastName());
        newUser.setEmail(teacherForm.getEmail());
        newUser.setActive(false);
        newUser.setRole(new Role(2, "Teacher"));

        if (!userService.saveNewUserWithRandomPassword(newUser)) {
            return false;
        }

        newTeacher.setUser(newUser);
        newTeacher.setSpecialization(teacherForm.getTeachingSubject());

        teacherRepository.save(newTeacher);

        return true;
    }

    public Optional<Teacher> getTeacherByFullName(String fullName) {

        String[] nameParts = fullName.split(" ");
        String lastName = nameParts[nameParts.length - 1];
        String firstName = String.join(" ", nameParts);

        return teacherRepository.findByUser_FirstNameAndUser_LastName(firstName, lastName);
    }

    public List<String> getAllTeachers() {
        return teacherRepository.findAll().stream().map(teacher -> teacher.getUser().getFullName()).toList();
    }
}
