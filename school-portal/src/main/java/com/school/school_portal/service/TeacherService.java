package com.school.school_portal.service;

import com.school.school_portal.dto.TeacherForm;
import com.school.school_portal.entity.Role;
import com.school.school_portal.entity.Teacher;
import com.school.school_portal.entity.User;
import com.school.school_portal.repository.TeacherRepository;
import com.school.school_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, UserService userService) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void saveTeacher(TeacherForm teacherForm) {

        User newUser = new User();
        Teacher newTeacher = new Teacher();

        newUser.setFirstName(teacherForm.getFirstName());
        newUser.setLastName(teacherForm.getLastName());
        newUser.setEmail(teacherForm.getEmail());
        newUser.setActive(false);
        newUser.setRole(new Role(2, "TEACHER"));

        userService.saveNewUserWithRandomPassword(newUser);

        newTeacher.setUser(newUser);
        newTeacher.setSpecialization(teacherForm.getTeachingSubject());

        teacherRepository.save(newTeacher);
    }
}
